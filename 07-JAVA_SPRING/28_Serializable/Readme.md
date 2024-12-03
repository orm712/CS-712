# 자바 직렬화
## 직렬화
> - 객체의 상태를 `바이트 스트림`으로 변환하여 파일이나 네트워크로 전달하거나 저장할 수 있게 하는 과정
> - 역직렬화: 바이트 스트림으로 변환된 데이터를 다시 객체로 복원하는 과정
> - JVM의 Runtime Data Area(Heap 또는 스택 영역)에 상주하고 있는 객체 데이터를 바이트 형태로 변환하는 기술과 직렬화된 바이트 형태의 데이터를 객체로 변환해서 JVM으로 상주시키는 형태

## Serializable 인터페이스
```java
public interface Serializable() {
    //아무 메서드도 구현되어 있지 않다.
}
```
> - 객체를 직렬화하려면 `java.io.Serializable` 인터페이스를 구현해야 한다.
> - `Serializable`은 마커 인터페이스로, 구현 시 특별한 메서드를 추가로 정의하지 않는다.
>   - 마커 인터페이스 : 일반적인 인터페이스와 동일하지만 사실상 아무 메소드도 선언하지 않은 인터페이스를 말한다.
>   - 단순히 해당 클래스의 객체가 직렬화 가능하다는 것을 JVM과 관련 API에 알리기 위한 신호

## transient 키워드
> - 보통 클래스의 멤버변수 전부 직렬화 대상에 해당된다.
> - 하지만 보안 상의 문제나 기타 이유로 멤버 변수의 일부를 제외하고 싶다면 `transient`를 사용하면 된다.
```java
public class User implements Serializable {
    private String id;
    private transient String password;
    private String email;

    //....
}
```

## serialVersionUID
> - 직렬화된 객체의 버전 관리를 위해 사용
> - 직렬화하면 내부에서 자동으로 SerialVersionUID라는 고유의 번호를 생성하여 클래스의 버전을 관리한다
> - 클래스의 변경 사항이 있어도 동일한 `serialVersionUID`가 있다면 역직렬화가 가능
>   - 같은 serialVersionUID만 직렬화/역직렬화가 가능하다.
> - 명시하지 않으면 JVM이 자동 생성하지만, 클래스가 변경되면 값도 변경되므로 명시적으로 선언하는 것이 좋다
>   - serialVersionUID가 선언되어 있지 않으면 클래스의 기본 해쉬값을 사용하게 된다

## 직렬화 동작 과정
### 직렬화
> - `java.io.ObjectOutputStream`을 사용해 객체를 직렬화한다
```java
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.obj"))) {
    oos.writeObject(myObject); //객체 저장
}
```

### 역직렬화
> - `java.io.ObjectInputStream`을 사용해 객체를 복원한다.
```java
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.obj"))) {
    MyClass myObject = (MyClass) ois.readObject(); //객체 복원
}
```

## 자바 직렬화의 장점과 단점
### 장점
> - 객체의 영속성 제공
>   - 객체의 상태를 파일, 데이터베이스, 네트워크 등 외부 저장소에 저장할 수 있다.
>   - 저장된 객체를 역직렬화하여 프로그램 종료 후에도 객체의 상태를 복원할 수 있다.
> - 네트워크 통신에 유용
>   - 객체를 직렬화하여 네트워크를 통해 다른 JVM으로 전달할 수 있다.
>   - RMI(Remote Method Invocation)와 같은 기술에서 사용됩니다.
> - JVM의 자동 처리
>   - ObjectOutputStream과 ObjectInputStream을 사용하여 자동으로 직렬화 및 역직렬화가 수행되므로 개발자는 복잡한 변환 로직을 구현할 필요가 없다.
>   - 데이터 타입이 자동으로 맞춰준다.
>   - 시스템이 종료되더라도 없어지지 않으며 영속화된 데이터이기 때문에 네트워크로 전송도 가능하다.
>     - 그리고 필요할 때 직렬화된 객체 데이터를 가져와서 역직렬화하여 객체를 바로 사용할 수 있게 된다.
> - 객체 그래프 직렬화
>   - 객체가 다른 객체를 참조하고 있어도, 해당 참조 객체도 함께 직렬화.
>   - 객체 간의 연관 관계를 유지한 상태로 저장 및 복원 가능.
> - 사용법이 간단
>   - Serializable 인터페이스를 구현하기만 하면 객체 직렬화 기능을 쉽게 적용할 수 있다.
>     - 기본 자바 라이브러리만 이용하더라도 직렬화/역직렬화 가능

### 단점
> - 성능 문제
>   - 직렬화/역직렬화 과정은 CPU와 메모리 리소스를 많이 사용.
>   - 대규모 데이터 처리나 고성능이 요구되는 시스템에서는 효율적이지 않다.
> - 보안 문제
>   - 역직렬화는 공격자가 악의적으로 조작된 객체를 생성하거나 시스템을 침투하는 보안 취약점을 야기할 수 있다.
>   - 이를 방지하려면 데이터 검증 로직을 추가하거나 ObjectInputStream의 readObject() 메서드를 재정의해야 한다.
> - 버전 호환성 문제
>   - 클래스 구조가 변경되면 기존 직렬화된 데이터를 역직렬화하는 데 문제가 생길 수 있다.
>   - 이를 해결하기 위해 serialVersionUID를 명시적으로 설정해야 하지만, 이 과정이 번거로울 수 있다.
> - 비효율적인 데이터 크기
>   - 기본 자바 직렬화는 데이터 크기가 커질 수 있어 네트워크 대역폭 및 저장소를 비효율적으로 사용.
> - 제어 부족
>   - 직렬화 과정은 JVM이 자동 처리하므로, 세부적인 제어가 어렵다.
>   - 이를 보완하기 위해 Externalizable 인터페이스를 사용해야 하지만 구현이 복잡하다.
> - 플랫폼 의존성
>   - 자바 직렬화는 자바 언어에 종속적이므로 다른 프로그래밍 언어와의 호환성이 떨어진다.
> - 영속성 관리 한계
>   - 파일이나 데이터베이스 같은 외부 시스템과 직접 연결된 저장소 관리를 효율적으로 처리하지 못한다.

---

## SpringBoot에서 JSON 변환 과정
1. 클라이언트 요청 → JSON 데이터를 수신
> - 클라이언트가 서버로 JSON 데이터를 전송하면 HTTP 요청의 Content-Type 헤더가 `application/json`으로 설정
>   - application/json : HTTP 요청의 Body가 JSON형식임을 서버에 알리는 헤더
2. HTTP 요청 처리 과정
> - DispatcherServlet 동작
>   - 모든 HTTP 요청은 Spring MVC의 `DispatcherServlet`에서 시작된다.
>   - DispatcherServlet은 요청을 적절한 컨트롤러 메서드에 라우팅한다.
> - HandlerMapping을 통해 컨트롤러 메서드 찾기
>   - URL 및 HTTP 메서드(GET, POST 등)에 따라 요청을 처리할 컨트롤러를 매핑한다.
> - HandlerAdapter를 통해 컨트롤러 실행
>   - 매핑된 컨트롤러 메서드를 호출하고 결과를 반환한다.
3. JSON → Java 객체 변환 (요청 데이터 처리)
> - 클라이언트가 보낸 JSON 데이터는 `@RequestBody`를 사용하여 자동으로 Java 객체로 변환
>   - @RequestBody가 Spring 내부 HttpMessageConverter를 사용하여 java 객체 -> json / json -> java 객체로 변환
>     - HttpMessageConverter는 인터페이스고, 각 상황에 맞는 구현체들을 매핑한다.(json, xml, ...)
>     - 요청의 Content-Type 헤더를 기반으로 적절한 HttpMessageConverter를 선택한다.
>     - 예: Content-Type: application/json → MappingJackson2HttpMessageConverter
> - Jackson 라이브러리 역할
>   - Spring Boot는 기본적으로 Jackson 라이브러리를 사용해 JSON 데이터를 Java 객체로 변환
>   - 요청 본문(JSON 데이터)은 `HttpMessageConverter`의 구현체인 `MappingJackson2HttpMessageConverter`에 의해 처리된다.
>     - MappingJackson2HttpMessageConverter 내부에 ObjectMapper를 포함하고 있는데, 이를 통해서 직렬화/역직렬화 진행
>   - 이 과정에서 JSON 필드 이름을 Java 객체의 필드 이름에 매핑
4. Java 객체 → JSON 변환 (응답 데이터 처리)
> - Jackson에 의한 직렬화
>   - 컨트롤러 메서드가 반환한 Java 객체는 다시 `HttpMessageConverter`를 통해 JSON으로 변환된다.
>   - 이 작업은 `MappingJackson2HttpMessageConverter`에 의해 처리된다.
> - 클라이언트로 JSON 응답 전송
>   - Spring Boot는 JSON 데이터를 HTTP 응답 본문에 포함하여 클라이언트로 전송한다.
>   - 응답 헤더에는 Content-Type: application/json이 설정.

## 내부 동작 상세
### HttpMessageConverter 역할
> - Spring MVC는 요청과 응답 데이터를 변환하기 위해 HttpMessageConverter 인터페이스를 사용한다.
> - Jackson 기반의 `MappingJackson2HttpMessageConverter`가 JSON 변환 작업을 처리한다.
> - 필요 시 커스터마이징하거나 다른 라이브러리(Gson 등)로 교체할 수도 있다.

### Jackson ObjectMapper
> - Jackson 라이브러리는 ObjectMapper를 사용해 JSON 데이터와 Java 객체 간의 변환을 수행한다.
> - SpringBoot는 자동으로 ObjectMapper를 빈으로 등록하며, 이를 이용해 변환 작업을 처리한다.

