# 순수함수가 무엇인지를 함수형 프로그래밍 매커니즘과 연관지어 설명해 주세요.

### Side Effect가 무엇인가요? 이를 모두 없애는 프로그래밍이 이상적이라고 할 수 있을까요?

> 함수가 인수의 값을 읽고 값을 반환하는 일을 제외하고 관찰 가능한 효과가 있는경우를 말함

- 비지역 변수, 정적 변수, 참조 값을 수정하거나 읽을 때
- 예외 발생
- I/O 수행

> side effect가 있는 경우에는 값의 변화가 있을 수 있다<br>
> 모든 side effects를 없애 프로그램을 작성한다면 프로그램의 예측가능성은 좋아 프로그램의
> 테스트 및 유지보수가 좋을지 몰라도, 데이터베이스 저장과 같은 I/O등 필수적인 side effect들도
> 있기 때문에 이상적이라고 생각하지 않는다

---

### 왜 함수형 프로그래밍 매커니즘을 사용한다고 생각하시나요?

> 자료 처리를 수학적 함수의 계산으로 취급하고 상태와 가변 데이터를 멀리하는 프로그래밍 패러다임

#### `명령형 프로그래밍 vs 함수형 프로그래밍`

`명령형 프로그래밍`  
(프로그래밍의 상태와 상태를 변경시키는 구문의 관점에서의 연산을 설명)

- 절차지향 프로그래밍
- 객체지향 프로그래밍

`선언형 프로그래밍`  
(어떻게할 것인가 보다는 무엇을 할 것인가를 표현)

- 함수형 프로그래밍(Functional Programming)
  - side effect
  - pure function
  - First-Class Object
  - Referential Transparency

> `Pure Fucntion`
>
> - 함수는 side effect가 없어야하며 외부 상태에 종속되지 않아야 한다
> - 동일한 입력에 대해 동일한 출력을 낸다
>
> `장점`
>
> - 테스트가 쉬움
> - 코드 유지관리성
> - 동시성 및 병렬성

> `First-Class Object`
>
> - 다른 객체들에 일반적으로 적용 가능한 연산을 모두 지원하는 객
>
> `로빈 포플스톤의 일급 객체 정의`
>
> - 함수의 실제 매개변수가 될 수 있음
> - 함수의 반환 값이 될 수 있음
> - 할당 명령문의 대상이 될 수 있음
> - 동일 비교의 대상이 될 수 있음
>
> 이러한 성질로 함수를 Highter order function을 만들 수 있다

> `Referential Transparency(참조 투명성)`
>
> - 프로그램 동작의 변경없이 관련 값을 대체할 수 있다면 표현식을 참조 상 투명하다
>
> ```
> val x = "Hello"
> val r1 = x + " World"
> val r2 = "Hello" + " World"
> val r3 = x + " World"
>
> val x = new StringBuilder("Hello")
> val r1 = x.append(" World").toString
> val r2 = x.append(" World"),toString
> ```
>
> 위 코드는 x에 대해 같은 작업을 해도 계속 같은 값을 내지만 아래의 코드는 r2 = "Hello World World"의
> 결과 값을 가짐으로 참조가 투명하지 않다

#### `함수형 프로그래밍을 사용하는 이유`

- 사용하는 모든 데이터가 변경 불가능(immutable)하고 함수는 부수 효과를 가지고 있지 않기 때문에 동시성과 관련된 문제를 원천적으로 차단
- 함수는 입력과 그에 대한 출력만을 책임지기 때문에 테스트가 쉽고 가독성이 좋음
- 고차 함수로 인해 함수들 간의 결합, 조합이 간결해지며, 익명 함수를 사용할 수 있음
- 코드 재사용성이 높음
- 깔끔하고 유지보수가 용이함

---

### 순수함수는 Thread Safe 한가요? 왜 그럴까요?

- 입력에만 의존: 함수의 반환 값이 오직 함수의 입력 인자에만 의존
- Side Effect 없음: 함수 실행이 외부 상태를 변경하지 않음

> 이러한 특성 때문에 순수 함수는 병렬 처리 환경에서도 안전하게 사용할 수 있다  
> 여러 스레드가 동시에 순수 함수를 호출하더라도, 각 호출은 독립적이며 서로 간섭하지 않는다  
> 이는 순수 함수가 전역 상태를 변경하지 않기 때문에 가능하며,
> 따라서 경쟁 조건(race condition)이나 데이터 경합(data race) 문제가 발생하지 않는다

## 고차함수에 대해 설명해 주세요.

- 함수의 형태로 리턴할 수 있는 함수

특징

- 함수는 변수의 형태로 저장 할 수 있
- 함수를 담은 변수를 인자로 받을 수 있다
- 함수 내부에서 변수에 함수를 할당할 수 있다
- 함수는 변수에 할당한 함수를 리턴할 수 있

다른 함수에 인자로 전달되는 함수를 `Callback Function`
이라 한다  
[참고](https://velog.io/@thyoondev/%EA%B3%A0%EC%B0%A8-%ED%95%A8%EC%88%98higher-order-function%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC)

---

`side effect`

- https://en.wikipedia.org/wiki/Side_effect_(computer_science)

`pure function`

- https://www.linkedin.com/pulse/understanding-concept-pure-functions-javascript-ankitaa-panpatil-oikaf
- https://medium.com/korbit-engineering/%ED%95%A8%EC%88%98%ED%98%95-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%EC%9D%B4%EB%9E%80-e7f7b052411f

`functional programming`

- https://ko.wikipedia.org/wiki/%ED%95%A8%EC%88%98%ED%98%95_%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D
- https://mangkyu.tistory.com/111

`First-Class Object`

- https://ko.wikipedia.org/wiki/%EC%9D%BC%EA%B8%89_%EA%B0%9D%EC%B2%B4
- https://velog.io/@reveloper-1311/%EC%9D%BC%EA%B8%89-%EA%B0%9D%EC%B2%B4First-Class-Object%EB%9E%80

`Referential Transparency`

- https://ko.wikipedia.org/wiki/%EC%B0%B8%EC%A1%B0_%ED%88%AC%EB%AA%85%EC%84%B1
- https://dowhateveryouwant.tistory.com/30

`higher order function`

- https://velog.io/@thyoondev/%EA%B3%A0%EC%B0%A8-%ED%95%A8%EC%88%98higher-order-function%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC
