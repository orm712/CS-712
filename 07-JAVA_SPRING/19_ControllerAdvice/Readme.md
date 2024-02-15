# ControllerAdvice & RestControllerAdvice
- 전역적으로 예외를 처리할 수 있는 어노테이션
- AOP(Aspect-Oriented Programming)를 기반으로, 여러 컨트롤러에 걸쳐 있는 공통의 문제들을 한 곳에서 관리
- @ExceptionHandler, @InitBinder, @ModelAttribute를 사용하여 컨트롤러에서 발생하는 예외를 처리할 수 있음
- @Component가 포함되어 있기 때문에, 빈으로 관리

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
> - @ControllerAdvice와 @ResponseBody를 합친 어노테이션
>   - 응답을 JSON 형태로 반환해줌

## @ExceptionHandler
> - AOP를 이용한 예외처리 방식
> - 메소드에 선언해 예외 처리를 하려는 클래스를 지정하면, 예외 발생 시 정의된 로직에 의해 처리된다.
```java
@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> handleException(PasswordNotMatchException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

```

```java
@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {

    // 특정 예외 처리
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Resource not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // 유효성 검사 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 모든 종류의 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An error occurred");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

```


## 유의할 점
> - 프로젝트 하나당 @ControllerAdvice를 하나만 사용하는 것이 좋다.
>   - 여러개를 사용하게 되면, 어떤 것이 먼저 적용될지 알 수 없기 때문
>   - basePackageClasses 및 basePackages와 같은 Selector를 사용해서 할 수는 있긴 함
>     - 근데, 구성 과정이 복잡하기도 하고 런타임 과정에 수행되어서, 성능에 악영향