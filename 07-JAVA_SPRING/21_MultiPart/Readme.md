# Multipart
> ### 클라이언트와 서버 간에 전송되는 HTTP 요청 또는 응답에서 여러 종류의 데이터를 동시에 전송하기 위해 사용되는 방식
> - 일반적으로 파일 업로드와 관련된 데이터를 전송하는데 주로 사용
> - HTTP 프로토콜은 기본적으로 텍스트 기반의 요청과 응답 처리
>   - 파일과 같은 이진 데이터를 전송해야할 때는 이진 데이터를 텍스트 형식으로 인코딩하는 것이 비효율적이고 제한이 있다.
>   - 멀티파트는 이러한 이진 데이터를 인코딩하지 않고 원본 형식으로 전송할 수 있도록 해준다.
> - 멀티파트 요청은 `Content-Type`헤더에 `multipart/form-data` 값을 가지며 여러개의 part로 구성된다.

## 클라이언트로부터 Multipart를 받아오는 방식
> [FileController.java](multipartTest%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2FmultipartTest%2Fcontroller%2FFileController.java)

## @RequestParam vs @RequestPart vs @RequestBody vs @ModelAttribute
### @RequestParam
> - 웹 요청의 파라미터(주로 쿼리 파라미터나 폼 데이터)를 메서드 매개변수에 바인딩할 때
>   - `String`, `int`, `boolean`등 간단한 데이터 타입에 사용
> - `/example?name=John`
```java
@GetMapping("/example")
public String example(@RequestParam("name") String name) {
    return "Hello, " + name;
}
```

### @RequestPart
> - 멀티파트 요청(파일 업로드 등)의 일부를 메서드 매개변수에 바인딩할 때
> - @RequestParam과 달리 멀티파트 데이터의 특정 부분을 처리하는 데 사용
> - 멀티파트와 함께 json객체도 같이 보내고 싶은 경우
```java
@PostMapping("/upload")
public String handleFileUpload(@RequestPart("file") MultipartFile file) {
    // 파일 처리 로직
    return "File uploaded!";
}
```

### @RequestBody
> - HTTP 요청의 body 전체를 메서드 매개변수에 바인딩
>   - JSON이나 XML 데이터 처리
> - SpringMVC의 MessageConverter가 `프레임워크`에 내장되어 있다.
>   - json객체를 java객체로 변환할 수 있다.
>   - 이를 Content-Type으로 구분한다.
>     - 따라서, 클라이언트의 Content-Type이 `application/json` 또는 `application/xml`이어야 한다.
```java
@PostMapping("/user")
public String createUser(@RequestBody UserReqDTO user) {
    // User 객체 처리 로직
    return "User created!";
}
```

### @ModelAttribute
> - 폼 데이터나 요청의 일부를 모델 객체에 바인딩하고, 선택적으로 뷰에 노출시킨다.
> - 폼 데이터를 모델 객체에 자동으로 바인딩
>   - `ModelAndView`객체의 Model에 자동으로 추가
> - 여러 쿼리 파라미터를 한 번에 바인딩할 때도 사용
> - `GET /user?name=John&age=30&email=john@example.com`
```java
@GetMapping("/user")
    public String getUserDetails(@ModelAttribute User user) {
        // User 객체는 쿼리 파라미터로부터 자동으로 값이 바인딩.
        return "userDetails";
    }
```