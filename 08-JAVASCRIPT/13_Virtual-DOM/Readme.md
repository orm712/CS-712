## 가상 DOM?
- `가상 DOM(Virtual DOM, VDOM)`이란, `UI의 가상 표현`이 `메모리상에 저장`되고, `ReactDOM`과 같은 라이브러리를 통해 `실제 DOM과 동기화`되는 **`프로그래밍 개념`**
- 이렇게 실제 DOM과 가상 DOM을 `동기화`하는 과정을 `재조정(Reconciliation)`이라고 함
- 이러한 형태는 React의 `선언적 API 구조`의 기반이 됨
	- 사용자가 *UI가 어떤 상태가 되길 원하는지* React에게 알려주면, React는 DOM이 해당 상태와 일치하는지 확인하고 갱신
		- 이를 통해 어트리뷰트 조작, 이벤트 핸들링, 수동 DOM 조작과 같은 작업들을 추상화할 수 있음
			- 개발자가 직접 해당 조작들을 하나하나 수행하지 않아도 됨을 의미
		- 따라서 개발자는 한 상태에서 다른 상태로의 전환을 효율적으로 수행할 방안에 대해 고민하지 않아도 됨.
- 이러한 재조정 과정에 활용할 *컴포넌트 트리와 관련된 추가 정보들*을 저장하기 위해 `Fiber`라는 내부 객체를 사용
### 주의 사항
- React 내부에서 `재조정`과 `렌더링`은 별도의 단계로 설계되어 있음
	- `재조정`: `재조정자(reconciler)`가 트리의 어느 부분이 변경되었는지 계산
	- `렌더링`: `렌더러`가 계산된 트리를 사용해 실제로 렌더링 된 애플리케이션을 업데이트
- 이러한 구조를 통해, `ReactDOM`과 `React Native`는 동일한 `재조정자`를 공유하되, 각각 `자체 렌더러`를 사용
	- 즉, DOM은 React 가 `렌더링할 수 있는 환경` 중 하나에 불과함
### 재조정(Reconciliation)?
- DOM을 `React Element 트리`의 `가장 최근 상태`와 일치하도록 **`효율적으로 업데이트하는 방법`** 을 결정하는 과정
	- React의 `render()` 함수는 항상 최신 상태의 `React Element 트리`를 반환하고자 함.
		- 즉, `state` 또는 `props`가 업데이트될 때 마다 다른 `React Element 트리`를 반환해야 함.
		- 이를 위해서는 React가 DOM을 가장 최근의 트리와 일치하도록 효율적으로 업데이트 할 수 있어야 함.
- 즉, 한 트리에서 다른 트리로 변환하기 위한 **`최소한의 연산 횟수`** 가 발생하는 알고리즘이 필요
	- 일반적인 알고리즘으로는 $O(n^3)$의 시간이 소요됨 [#](https://grfia.dlsi.ua.es/ml/algorithms/references/editsurvey_bille.pdf)
- 이를 위해 React에서는 아래 `두 가지 가정을 기반`으로 한 휴리스틱 $O(n)$ 알고리즘을 사용
	1. 두 `다른 타입`의 엘리먼트들은 `서로 다른 트리`를 만든다.
	2. 개발자는 `key` prop을 통해 어떤 하위 엘리먼트가 여러 렌더링에 대해 안정적인지(동일한 형태를 띄는지) 힌트를 줄 수 있음
#### 디핑 알고리즘(Diffing Algorithm)
두 트리의 차이를 비교하는 `디핑 알고리즘`에서는 먼저 `루트 엘리먼트`를 비교하고, `루트 엘리먼트`의 타입에 따라 다른 동작을 수행
##### 다른 타입의 엘리먼트
- `루트 엘리먼트`가 다른 타입을 가질때마다, React는 이전 트리를 허물고 새로운 트리를 처음부터 구축
	- ex) 루트 엘리먼트가 `<a>` 에서 `<img>`로 바뀌거나, `<Article>`에서 `<Comment>`로 바뀌는 등의 경우
- 이전 트리를 허물때, 이전 `DOM Node`들도 파괴됨
	- 이때, 해당 `DOM Node`들의 컴포넌트 인스턴스들은 `componentWillUnmount()`를 수신
	- 루트 아래 모든 컴포넌트들은 마운트 해제되고 상태가 파괴됨
- 이후 새로운 트리를 만들 때, 새로운 `DOM Node`들이 DOM에 삽입됨
	- 이때, 해당 `DOM Node`들의 컴포넌트 인스턴스들은 `UNSAFE_componentWillMount()`를 수신한 뒤 `componentDidMount()`를 수신
- 이전 트리와 관련된 상태들은 모두 파괴됨
##### 동일한 타입의 DOM 엘리먼트
- 같은 타입의 두 `React DOM 엘리먼트`에 대해, React는 `DOM Node`는 유지하되, `어트리뷰트`를 살펴보고 `변경된 값`만 갱신함
```html
# 변경 전
<div className="before" title="stuff" />
# 변경 후, React는 비교 후 바뀐 className만 반영
<div className="after" title="stuff" />
```
##### 동일한 타입의 컴포넌트 엘리먼트
- 컴포넌트가 갱신된 경우, 인스턴스는 동일하게 유지되므로 상태는 렌더링간에 변화가 발생하지 않음
- React는 기존 컴포넌트의 props를 새 엘리먼트와 일치하도록 갱신
- 이후 `UNSAFE_componentWillReceiveProps()`와 `UNSAFE_componentWillUpdate()`, 그리고 `componentDidUpdate()`를 호출
- 이어서 `render()`가 호출된 뒤 재귀적으로 디핑 알고리즘이 호출됨
##### 자식에 대한 디핑 알고리즘 재귀
- 기본적으로 React는 `DOM Node`의 자식 노드들을 한 번에 순회하고, 차이가 있을 경우 갱신
##### `key` prop
- 상기한 자식 노드간 비교를 나이브하게 구현할 경우, 아래와 같은 케이스에서 비효율적으로 렌더링이 이뤄짐
```html
# 전
<ul>
  <li>Duke</li>
  <li>Villanova</li>
</ul>

# 후
# 만약 요소를 1대1로 비교할 경우, 위의 Duke는 Connecticut과, Villanova는 Duke와 비교하게 됨
# 따라서 이미 렌더링된 Duke와 Villanova를 재활용하지 못함
<ul>
  <li>Connecticut</li>
  <li>Duke</li>
  <li>Villanova</li>
</ul>
```
- 이를 방지하고자, React에서는 `key` prop을 사용해 이전 트리에서 같은 `key`를 가지는 자식과 비교하도록 함.
- `key`는 주로 데이터에서 발견할 수 있는 `고유한 ID`나, 데이터를 해시한 값을 사용
	- 배열에서의 항목의 `인덱스` 값을 `key`로 쓸 수도 있지만, 이는 배열의 재정렬이나 항목 간 이동 발생시 인덱스가 바뀌므로 원치않는 방식으로 갱신이 이뤄질 수 있음
		- [키를 index로 사용해 문제가 발생하는 케이스](https://legacy.reactjs.org/redirect-to-codepen/reconciliation/index-used-as-key)
		- [위 경우를 보완한 케이스](https://legacy.reactjs.org/redirect-to-codepen/reconciliation/no-index-used-as-key)
- `key`는 `형제(sibling)`사이에서만 고유하면 되며, 전역적으로 고유할 필요는 없음
### React의 디자인 원칙
- [React의 디자인 원칙](https://legacy.reactjs.org/docs/design-principles.html)의 핵심은 다음과 같음
	- UI에 모든 업데이트가 `즉시 반영되어야 할 필요는 없음`
		- React는 하나의 틱 동안 트리를 재귀적으로 순회하며 렌더링 함수 호출해 업데이트된 트리를 계산해야 함
		- 따라서 이들을 즉시 반영하려다 프레임이 떨어지거나, UX가 불편해질 수 있음
		- 따라서, 일부 업데이트를 지연시킬 수 있으며, 경우에 따라 데이터들을 통합해 일괄 업데이트 할 수 있음(데이터가 프레임 속도보다 빠르게 도착한 경우)
	- `다른 유형`의 업데이트들은 `다른 우선순위`를 가져야 함
		- 근본적으로 React는 `UI 구축`을 위한 라이브러리이므로, 화면에서 벗어난 것이 있다면 이와 관련된 모든 로직들을 `지연`시킬 수 있음
		- 예를 들어, 애니메이션 업데이트는 데이터 저장소의 값 업데이트보다 더 빨리 완료되어야 함
		- 이를 통해 프레임 저하를 방지할 수 있음
	- `풀(pull) 기반 접근 방식*`에서는 `작업 스케줄링*` 방법을 `프레임워크(React)`가 대신 결정해줌
		- 이는 `푸시(push) 기반 접근방식*`에서 `앱(프로그래머)`가 직접 결정해야하는 것과 대조적
---
- `풀 기반 접근 방식`: 필요할 때까지 계산을 지연시킬 수 있는 접근 방식
- `푸시 기반 접근방식`: 새 데이터를 사용할 수 있을 때마다, 계산이 이뤄지는 방식
- `작업 스케줄링`: `작업*`이 수행될 **시점**을  결정하는 일련의 과정
- `작업(work)`: 수행되어야 하는 연산. 주로 업데이트(`setState`와 같은)의 결과임
### React Fiber [# 참고 문서](https://github.com/acdlite/react-fiber-architecture)
- `React Fiber`란 React 16에 등장한 새로운 `재조정 엔진`
- 이를 통해 가상 DOM의 `점진적 렌더링(incremental rendering)`을 가능케 하는 것이 목표.
	- `점진적 렌더링`: 렌더링 작업을 청크로 분할하여, 여러 프레임에 걸쳐 분산하는 기능
		- 우선순위가 높은 업데이트 사항부터 먼저 처리해나가는 렌더링 방식
- 이외에도 새로운 업데이트 발생시 기존의 작업을 `일시 중지(pause)`, `중단(abort)`,  `재사용(reuse)` 할 수 있는 기능, 여러 유형의 업데이트에 `우선순위를 지정`하는 기능, 동시성 프로그래밍을 위한 기본 기능 등의 주요기능이 존재
#### 등장 배경
- Fiber 이전의 React는 `작업 스케줄링`을 활용하지 않아, 업데이트가 발생하면 모든 하위 트리가 즉시 재렌더링 됨
- 이를 방지하고자, React의 핵심 알고리즘을 개편하는 것이 Fiber의 목표
#### 기능
##### 작업 스케줄링
- React에서 스케줄링을 활용할 수 있도록 해야 하므로, 다음의 기능이 구현되어야 함
	- 작업의 `중지 및 재개`가 가능해야 함
	- `다양한 유형의 작업들`에게 `우선순위를 할당`할 수 있어야 함
	- `이전에 완료한 작업의 값`을 `재사용`할 수 있어야 함
	- 더 이상 `필요하지 않은 작업`은 `중단할 수 있어야 함`
- 이를 위해, 먼저 작업을 작은 단위인 `Fiber`로 세분화 함
	- React 컴포넌트를 `v = f(d)`의 꼴로 표현할 때, React 앱을 렌더링 하는건 *또 다른 함수에 대한 호출*이 포함된 함수를 호출하는 것과 유사
- UI를 다룰 때, 한 번에 많은 작업을 실행하려하면 애니메이션 프레임이 떨어지게 됨
- 이를 해결하기 위해 `최신 브라우저`와 `React Native`는 다음 두 API를 구현하여 사용
	- `requestIdleCallback`: 낮은 우선순위의 함수를 `유휴 기간(Idle Period)`동안 호출되도록 스케줄링
	- `requestAnimationFrame`: 높은 우선순위의 함수를 `다음 애니메이션 프레임`에 호출되도록 스케줄링
- 이러한 API의 구현은 렌더링 작업을 `증분 단위(Incremental Unit)`로 나눌 수 있을때 가능
	- 일반적인 함수 호출 방식인 `콜 스택`에 의존하게 되면, 스택이 비워질 때까지 작업을 수행하므로 이러한 동작이 불가능
- `React Fiber`는 `React Component`에 특화된, 재구현된 `콜 스택`이라고 볼 수 있으며, `Fiber`는 하나의 가상 스택 프레임이라고 볼 수 있음
- `React Fiber`의 구현을 통해 `스택 프레임`을 `메모리에 보관`해두었다, `다시 실행`하는 것이 `가능`해짐
	- 이는 `React Fiber`의 목표인 `작업 스케줄링`을 가능케 하는것은 물론, `동시성`, `오류 경계(Error Boundary)`과 같은 기능도 사용할 수 있음
#### Fiber의 구조
- `Fiber`는 `컴포넌트`, `입력(input)`, `출력(output)`에 대한 정보를 포함하는 `JS 객체`
- `스택 프레임`이면서, `컴포넌트의 인스턴스`라고 할 수 있음
- `Fiber`의 `핵심 필드`
	- `type`, `key`
		- React 엘리먼트로부터 복사해온 `type`, `key` 값
		- `type`은 `Fiber`에 대응되는 컴포넌트의 유형에 대해 설명
		- 두 필드 모두 `재조정`과정에서 재사용 가능 여부를 판별할 때 사용
	- `child`, `sibling`
		- 다른 `Fiber`를 가리키는 필드로, 재귀적 트리 구조를 묘사함
		- `child`: 컴포넌트의 `render` 메서드가 반환하는 값에 대응됨
		- `sibling`: 컴포넌트의 `render` 메서드가 여러 자식을 반환하는 경우, 자식 Fiber들을 단방향 링크드리스트 형태로 묶어놓은 값
			- 이때, 첫 번째 자식이 링크드리스트의 head임
	- `return`
		- 프로그램이 현재 Fiber를 처리하고, 반환해야 할 Fiber 값
			- 개념적으로, 스택 프레임의 `반환 주소(return address)`와 동일
			- 즉, 쉽게 `부모 Fiber`라고 보면 됨
	- `pendingProps`, `memoizedProps`
		- 개념적으로, `props`는 함수의 인자값
		- `pendingProps`는 실행 시작 시점에 설정되고, `memoizedProps`는 종료 시점에 설정됨
		- 만약 두 값이 같다면, Fiber의 이전 출력 값을 재사용할 수 있다는 뜻
	- `pendingWorkPriority`
		- Fiber가 내포하고 있는 작업의 우선순위를 나타내는 숫자 값
		- 0인 `NoWork`를 제외하고, 값이 클수록 우선순위가 낮음을 의미
		- 스케줄러는 이 우선순위 값을 사용해, 다음으로 수행할 작업을 탐색함
	- `alternate`
		- 이 Fiber의 대체 Fiber를 의미하는 필드
		- 컴포넌트 인스턴스에는 대응되는 최대 2개의 Fiber가 존재
			- 즉, `현재 Fiber(플러시* 된)`와 `작업 진행 중인 Fiber`가 존재
				- `플러시`: `출력(output)`을 화면에 `렌더링`하는 것
			- 둘은 각각 서로에게 대응됨
		- Fiber의 `alternate`는 `cloneFiber`라는 함수를 이용해 만들어지며, 이미 존재하는 경우 해당 값을 재사용함
	- `output`
		- 개념적으로 `함수의 반환값`에 대응되는 개념
		- 모든 `Fiber`는 `output`이 있지만, 오직 `리프 노드`에서만 `호스트 컴포넌트*`에 의해 생성됨
			- 생성된 `output`은 트리 위로 전달 됨
			- `호스트 컴포넌트`: React 앱의 `리프 노드`. `렌더링 환경`에 따라 다르지만, 브라우저 환경에서는 보통 `div`, `span`같은 엘리먼트를 의미
		- `output`은 렌더러가 `렌더링 환경`에게 `변경 사항을 플러시` 할 수 있도록 최종적으로 렌더러에게 전달되어야 함
### React 18 이후의 가상 DOM
- 상기한 디핑 알고리즘 방식은 크고, 복잡한 컴포넌트 계층 구조에 대해서는 한계가 존재
	- Javascript의 `단일 스레드`라는 구조적 한계 때문에, UI 업데이트 관련 코드 동작이 오래 걸리면 화면이 충분히 빠르게 갱신되지 못하고 끊김이 발생
- 이를 보완하고자, `React 18`에서 `Concurrent Mode`의 이점을 활용한 개선된 `재조정(Reconciliation)` 알고리즘을 사용
- `Concurrent Mode`를 통해 React는 메인 스레드가 블락되지 않고도 여러 작업들을 병렬적으로 수행할 수 있게 됨
- React 18의 `재조정 알고리즘`은 다음과 같음
	- 먼저 `재조정 작업`을 `fiber`라고 부르는 작은 유닛들로 나누고, 이들의 중요도에 따라 `우선순위`를 메김
	- 이후 React는 `우선순위를 기반`으로 재조정 프로세스를 효율적으로 중단 및 재개함
### Vue.js의 가상 DOM [# 참고 문서](https://vuejs.org/guide/extras/rendering-mechanism)
- Vue.js 역시 `가상 DOM`을 사용
	- 다만, React와 여타 다른 가상 DOM을 구현하는 라이브러리들과 다른점이 존재
		- 바로 `컴파일 시간`에 템플릿을 `정적으로 분석`해, `가상 DOM`의 런타임 성능을 향상시키고자 몇가지 `최적화`를 진행한다는 점
		- 이를 Vue.js에서는 `컴파일러에 알려진 가상돔 최적화(Compiler-Informed Virtual DOM)` 라고 부름
- `최적화 기법`
	- `정적 호이스팅 (Static Hoisting)`
		```js
			<div>
			  <div>foo</div> <!-- 호이스팅 됨 -->
			  <div>bar</div> <!-- 호이스팅 됨 -->
			  <div>{{ dynamic }}</div>
			</div>
		```
		- 템플릿에서 동적 바인딩이 포함되지 않은 부분에 대한 비교를 건너 뜀
	- `패치 플래그`
		```js
			if (vnode.patchFlag & PatchFlags.CLASS /* 2 */) {
			  // 노드의 패치 플래그에서 클래스 변동에 대한 비트 값이 1인 경우
			  // 클래스 값에 변동이 생겼음을 알 수 있음
			}
		```
		- 클래스, 텍스트, 스타일, props 등의 `요소`들에 대해 `변동이 있는지를 기록`하는 `플래그`인 `패치 플래그`를 통해 어떤 업데이트 작업이 필요한지 확인
	- `트리 병합 (Tree Flattening)`
		```jsx
			<div> <!-- 루트 블록 -->
			  <div>...</div>         <!-- 추적 안 됨 -->
			  <div :id="id"></div>   <!-- 추적됨 -->
			  <div>                  <!-- 추적 안 됨 -->
			    <div>{{ bar }}</div> <!-- 추적됨 -->
			  </div>
			</div>
			<!-- 위 경우, 트리 병합을 통해 아래와 같이 평탄화된 형태로 최적화 할 수 있음 -->
			<div> (루트 블록)
				<div :id="id"></div>
				<div>{{ bar }}</div>
			</div>
		```
		- 트리에서 `정적인 자식 노드들(동적 바인딩이 포함되지 않은)은 제외`하고, `동적 하위 노드`들만 `포함`하는 `트리 병합`을 수행해, 가상 DOM 재조정 중에 통과해야 하는 노드의 수를 크게 줄임
	
# 참고
- [Virtual DOM and Internals – React](https://legacy.reactjs.org/docs/faq-internals.html)
- [Reconciliation – React](https://legacy.reactjs.org/docs/reconciliation.html)
- [acdlite/react-fiber-architecture: A description of React's new core algorithm, React Fiber](https://github.com/acdlite/react-fiber-architecture)
- [Rendering Mechanism | Vue.js](https://vuejs.org/guide/extras/rendering-mechanism)