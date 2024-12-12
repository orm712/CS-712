# TDD (이론편)
> - Test Driven Development
> - 테스트 코드를 먼저 작성한 후 해당 테스트를 통과하는 실제 코드를 작성하는 개발 방식
> - 코드의 품질을 높이고 유지보수를 용이하게 하며, 개발 과정에서 발생할 수 있는 문제를 초기에 발견할 수 있도록 도와준다.

## TDD의 기본 사이클
![img.png](img.png)
> - Red(실패하는 테스트 작성)
>   - 기능 요구사항을 기반으로 테스트 코드를 작성한다.
>   - 아직 구현 코드가 없으므로 테스트는 실패한다.
>   - 테스트는 시스템이 어떻게 동작해야 하는지를 정의한다.
> - Green (테스트 통과를 위한 최소한의 코드 작성)
>     - 작성한 테스트를 통과하기 위해 최소한의 코드를 작성한다.
>     - 목표는 테스트를 "Green 상태"로 만드는 것이며, 최적화나 구조화는 하지 않는다.
> - Refactor (코드 및 테스트 정리)
>   - 테스트가 통과된 후 코드를 리팩토링한다.
>   - 중복을 제거하거나 코드 구조를 개선하여 가독성을 높이고 유지보수성을 향상시킨다.
>   - 테스트가 여전히 통과되는지 확인한다.

## JUnit
> - 가장 널리 사용되는 자바 단위 테스트 프레임워크
> - 테스트 메서드를 작성하고 실행할 수 있는 다양한 어노테이션과 유틸리티를 제공
```java
@Test
public void testAddition() {
    Calculator calculator = new Calculator();
    assertEquals(5, calculator.add(2, 3));
}
```

## Mockito
> - 의존성을 모의(Mock)하여 단위 테스트를 보다 쉽게 작성할 수 있게 해준다.
> - 외부 시스템이나 데이터베이스에 의존하지 않고 특정 객체의 동작을 시뮬레이션한다.

## AssertJ
> - 가독성이 높은 테스트 코드를 작성하도록 도와주는 어서션 라이브러리입니다.
> - assertEquals보다 더 유연하고 표현력 있는 어서션을 제공합니다.

## Spring Boot Test
> - Spring Boot 애플리케이션에서 TDD를 적용할 때 유용합니다.
> - 통합 테스트를 쉽게 작성할 수 있도록 다양한 기능을 제공합니다.

## Java TDD 예제
### Red : 실패하는 테스트 작성
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberUtilsTest {
    @Test
    public void isEven_shouldReturnTrueForEvenNumbers() {
        assertTrue(NumberUtils.isEven(2));
    }

    @Test
    public void isEven_shouldReturnFalseForOddNumbers() {
        assertFalse(NumberUtils.isEven(3));
    }
}

```

> - 테스트를 실행하면 NumberUtils 클래스와 isEven 메서드가 없어서 실패한다.

### Green : 테스트 통과를 위한 코드 작성
```java
public class NumberUtils {
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}

```
> - 테스트를 다시 실행하면 성공한다.

### Refactor : 코드 리팩토링
> - 이 단계에서는 특별히 리팩토링할 부분이 없지만, 필요하다면 가독성을 높이는 작업을 수행한다.

## TDD 장점
> - 버그 감소: 문제를 초기에 발견할 가능성이 높아진다.
> - 코드 품질 향상: 테스트를 먼저 작성하기 때문에 깔끔하고 테스트 가능한 코드 작성이 유도된다.
> - 빠른 피드백: 작은 단위의 테스트로 개발자가 실수를 빠르게 인지할 수 있다.
> - 안전한 리팩토링: 테스트가 존재하므로 코드를 수정할 때도 기존 기능이 깨질 위험이 줄어든다.

## TDD 단점
> - 초기 개발 시간 증가: 테스트 코드 작성에 시간이 추가적으로 소요된다.
> - 러닝 커브: 처음 TDD를 도입하면 개발 문화와 습관을 바꾸는 데 시간이 걸릴 수 있다.
> - 테스트 유지보수 비용: 시스템이 커질수록 테스트 코드 역시 유지보수 대상이 된다.
