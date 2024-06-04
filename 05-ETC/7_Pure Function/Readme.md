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

 
### 왜 함수형 프로그래밍 매커니즘을 사용한다고 생각하시나요?

> 자료 처리를 수학적 함수의 계산으로 취급하고 상태와 가변 데이터를 멀리하는 프로그래밍 패러다임


####  명령형 프로그래밍 vs 함수형 프로그래밍

`명령형 프로그래밍`
- 절차지향 프로그래밍
- 객체지향 프로그래밍

`함수형 프로그래밍(Functional Programming)`

- side effect
- pure function
- First-Class Object
- Referential Transparency
  

> `Pure Fucntion`
> - 함수는 side effect가 없어야하며 외부 상태에 종속되지 않아야 한다
> - 동일한 입력에 대해 동일한 출력을 낸다
>
>  `장점`
> - 테스트가 쉬움
> - 코드 유지관리성
> - 동시성 및 병렬성

> `First-Class Object`
> - 다른 객체들에 일반적으로 적용 가능한 연산을 모두 지원하는 객
> 
>  `로빈 포플스톤의 일급 객체 정의`
> - 함수의 실제 매개변수가 될 수 있음
> - 함수의 반환 값이 될 수 있음
> - 할당 명령문의 대상이 될 수 있음
> - 동일 비교의 대상이 될 수 있음

> `Referential Transparency`
> 
> 

### 순수함수는 Thread Safe 한가요? 왜 그럴까요?

    

## 고차함수에 대해 설명해 주세요.

> 함수의 형태로 리턴할 수 있는 함수
> 
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

`higher order function`

- https://velog.io/@thyoondev/%EA%B3%A0%EC%B0%A8-%ED%95%A8%EC%88%98higher-order-function%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC
