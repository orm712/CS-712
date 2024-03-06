## Java 에서 Annotation 은 어떤 기능을 하나요?

### 어노테이션의 기본 사용

1. 컴파일러에 정보 제공

   `추가 정보를 제공하여 특정 부분의 코드에 대한 경고나 오류를 발생시킬 수 있다`

   ```java
   @Override
       public void someMethod() {
           // method implementation
       }
   ```

2. 컴파일 단계 혹은 배포 단계에서의 처리

   `소프트웨어 도구는 주석 정보를 처리하여 코드, XML 파일 등을 생성할 수 있다`

   ```java
    //컴파일 단계에서 처리
    //Lombok 라이브러리의 @Getter을 사용해서 getter를 자동으로 생산
    import lombok.Getter;

    public class Example {
        @Getter
        private String name;
    }

   ```

3. 런타임시에 처리

   `런타임 시 검사할 수 있다`

   ```java
   //@Autowired를 사용해서 런타임시 의존성을 추가
    import org.springframework.beans.factory.annotation.Autowired;

    public class ExampleService {
        @Autowired
        private SomeDependency dependency;
    }

   ```

### 별 기능이 없는 것 같은데, 어떻게 Spring 에서는 Annotation 이 그렇게 많은 기능을 하는 걸까요?

> 스프링의 특징으로는 `제어반전`, `의존성 주입`, `관점 지향 프로그래밍`등의 특징이 있다
> Annotation은 이러한 특징들을 `@Autowired`, `@Component`, `@Aspect @Before ...` 등의 어노테이션으로 스프링의 특징을 지원해주고 사용하면 가독성면에서도 좋은 점을 보여준다

### Lombok의 @Data를 잘 사용하지 않는 이유는 무엇일까요?

- @data

  1. @ToString : toString 메소드를 자동생성
  2. @Getter : 모든 필드의 getter메소드 가 자동 생성
  3. @Setter : 모든 필드의 setter 메소드가 자동 생성
  4. @EqualsAndHashCode : equals, hashCode 메서드 자동생성
  5. @RequiredArgsConstructor : final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 자동생성.

- 문제점

1.  중요한 데이터의 Setter도 자동으로 생성하기 때문에 안정성이 떨어진다
2.  @EqualsAndHashCode을 사용했을 때 동일 타입의 두개의 변수를 선언해 두었다 순서를 변경하는 작업을 했을 경우 생성자의 파라미터를 필드의 선언 순서대로 변형하게 되는데 IDE에서 제공하는 리팩토링은 동작하지 않고 두필드가 동일 타입이라 기존 소스에서 오류가 발생하지 않지만 사실상 값이 바뀌어 들어가는 문제가 생긴다
