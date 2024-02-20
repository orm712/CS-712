# ControllerAdvice & RestControllerAdvice
> - Spring Framework에서 전역적으로 예외를 처리할 수 있는 어노테이션
> - AOP(Aspect-Oriented Programming)를 기반으로, 여러 컨트롤러에 걸쳐 있는 공통의 문제들을 한 곳에서 관리
> - @ExceptionHandler, @InitBinder, @ModelAttribute를 사용하여 컨트롤러에서 발생하는 예외를 처리할 수 있음
> - @Component가 포함되어 있기 때문에, 빈으로 관리
> - 별도의 try-catch문을 사용하지 않고, 예외를 처리할 수 있음
> - 직접 정의한 에러 응답을 일관성있게 클라이언트에게 내려줄 수 있음

## @ControllerAdvice
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ControllerAdvice {
    ~~
}
```

## @RestControllerAdvice
> - @ControllerAdvice와 @ResponseBody를 합친 어노테이션
>   - 응답을 JSON 형태로 반환해줌
> - REST API를 개발할 때 클라이언트에게 예외 상황을 JSON형태로 일관되게 전달할 수 있게 해줌
```java
@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME) 
@Documented 
@ControllerAdvice 
@ResponseBody 
public @interface RestControllerAdvice {
    ~~
} 
```


## @ExceptionHandler
> - AOP를 이용한 예외처리 방식
> - 메소드에 선언해 예외 처리를 하려는 클래스를 지정하면, 예외 발생 시 정의된 로직에 의해 처리된다.
> - 흐름
>   - 컨트롤러 메서드 실행 -> 예외 발생 -> DispatcherServlet 포착 -> @RestControllerAdvice 클래스 검색 -> @ExceptionHandler 메서드 검색 -> 처리
```java
public ResponseEntity<?> signIn(SignInReqDTO signInReqDTO){
        User user = authRepository.findByEmail(signInReqDTO.email())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자"));

        if(!passwordEncoder.matches(signInReqDTO.password(), user.getPassword())){
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        Token token = jwtProvider.generateToken(user.getEmail(), user.getRole());

        try{
            refreshTokenRedisRepository.save(RefreshToken.builder()
                    .email(user.getEmail())
                    .refreshToken(token.refreshToken())
                    .build());
        } catch (Exception e){
            throw new DatabaseException("DB 접근 오류");
        }

        SignInResDTO signInResDTO = new SignInResDTO(token.accessToken(), token.refreshToken());

        return ResponseEntity.ok().body(signInResDTO);
    }
```

```java
@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<String> handleDatabaseException(DatabaseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    ...
}

```

## 내가 쓰는 구현 방식
```java
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User not found"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "U002", "Password not match"),
    ...

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}

```
```java
@RestControllerAdvice
public class AuthControllerAdvice {

    private ResponseEntity<?> buildResponseEntity(ErrorCode errorCode, String message){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", errorCode.getStatus().value());
        body.put("error", errorCode.code());
        body.put("message", message != null ? message : errorCode.message());

        return new ResponseEntity<>(body, errorCode.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponseEntity(ErrorCode.USER_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<?> handleDatabaseException(DatabaseException e) {
        return buildResponseEntity(ErrorCode.DATABASE_ERROR, e.getMessage());
    }

    ...
}

```

## 유의할 점
> - 프로젝트 하나당 @ControllerAdvice를 하나만 사용하는 것이 좋다.
>   - 여러개를 사용하게 되면, 어떤 것이 먼저 적용될지 알 수 없기 때문
>   - basePackageClasses 및 basePackages와 같은 Selector를 사용해서 할 수는 있긴 함
>     - 근데, 구성 과정이 복잡하기도 하고 런타임 과정에 수행되어서, 성능에 악영향