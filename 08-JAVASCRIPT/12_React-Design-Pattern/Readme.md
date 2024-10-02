## React의 디자인 패턴
`React`에서는 웹 화면을 구성하기 위해 *버튼, 리스트와 같은 요소들*을 사용할 때 `최적화 및 간소화 된 방법`을 제공합니다.  
또한, `복잡하고 어려운 인터페이스`를 구현할 수 있도록 `컴포넌트`, `프롭스`, `상태`라는 세 가지 **핵심 콘셉트**로 웹 화면 인터페이스를 구조화 합니다.  
`React`는 `컴포넌트 중심 라이브러리`이므로, 각 디자인 요소들에 대해 완벽히 매핑할 수 있으며 `모듈적인 방향으로 사고`할 수 있게 해줍니다.  
### React의 핵심 콘셉트
#### 컴포넌트
`React 앱에서 사용되는 블록`으로, 임의의 매개변수(`props`)를 받아 React 요소를 반환하는 `JS 함수`라고 볼 수 있습니다. 따라서 `함수 컴포넌트(function components)`라고도 불립니다.  
본질적으로 React 앱은 `컴포넌트 내부의 컴포넌트들`이므로, 개발자는 *페이지를 빌드하는게 아니라*, `컴포넌트를 빌드`하는 것입니다.  
또한 컴포넌트를 사용하므로써, `UI`를 `독립적이며 재사용 가능한 부분`으로 `분할`할 수 있게 됩니다.  
컴포넌트는 `함수`, `클래스` 두 가지의 형태로 구성할 수 있으며 `React Hooks`가 등장하기 이전에는 `상태 관리`, `렌더링 생명주기별 동작` 지정을 위해서는 `클래스 컴포넌트`의 사용이 강제되었으나 현재는 `함수형 컴포넌트로`도 동일하게 동작할 수 있습니다.  
#### 프롭스
`프로퍼티`의 준말로, `React`에서 `컴포넌트 내부 데이터`를 가리키는 말입니다.  
`컴포넌트 호출` 안에 작성되며, 작성된 값은 컴포넌트에게 전달됩니다.  
이러한 작성은 `HTML 태그`의 `어트리뷰트`와 동일한 구문(ex. `<Tag props="v">`)으로 이뤄집니다.  
프롭스의 값은 컴포넌트 빌드 전 결정되며, 읽기 전용 값으로 컴포넌트에게 전달됩니다.  
#### 상태
*컴포넌트가 존재하는 동안* `변경될 수 있는 정보`를 `저장`하는 객체입니다.  
## 컨테이너 - 프레젠테이션 패턴
```js
function Container() {
	// 비즈니스 로직을 수행하는 컨테이너 컴포넌트
	// do something like API Communication
	const [fetchedData, setFetchedData] = useState();
	useEffect(()=>{
		fetch(...).then(res=>setFetchedData(res));
	}, []);
	// 컨테이너 컴포넌트로부터 데이터 전달받아
	// 화면에 표현하는 프레젠테이션 컴포넌트
	return <Presentation data={fetchedData} />
}
```
`컨테이너-프레젠테이셔널(Container-Presentational) 패턴`이란, `비즈니스 로직`과 `뷰`를 `분리`해 `관심사 분리(SoC)`를 이끌어 낼 수 있는 디자인 패턴을 말합니다.  
주로 데이터를 `시각화하는 방법`에 관심이 있는 `프레젠테이셔널 컴포넌트`와 이를 실제 `애플리케이션 로직`을 수행하는 `컨테이너 컴포넌트`로 감싸는 형태로 구현됩니다.  
### 프레젠테이션 컴포넌트
`프레젠테이션 컴포넌트`는 `프롭스(props)`를 통해 데이터를 수신하며, 수신 받은 데이터를 *수정하지 않고* 원하는 방식으로 표현하는 역할을 수행합니다.  
주로 `props`를 통해 데이터를 수신하므로 별도의 상태를 관리하지 않는 것이 대부분입니다.(다만, UI 표현에 필요하다면 상태를 사용하는 경우도 있습니다.)  
### 컨테이너 컴포넌트
`컨테이너 컴포넌트`는 그 안에 포함된 `프레젠테이션 컴포넌트`에게 데이터를 전달하며, `프레젠테이션 컴포넌트` 외에는 아무것도 렌더링하지 않습니다.  
### 훅
```js
function Presentation() {
	// 비즈니스 로직을 수행하는 훅을 호출
	const fetchedData = useApiHook();
	return <div> {fetchedData} <div/>
}
```
대부분의 경우, `컨테이너-프레젠테이션 패턴`은 `React Hook`으로 대체할 수 있습니다.  
`컨테이너 컴포넌트`에서 수행하던 작업을 수행하는 `커스텀 훅`을 만든 뒤, 해당 훅으로 부터 전달받은 데이터를 `프레젠테이션 컴포넌트`에서 사용하면 됩니다.  
### 장점
- 관심사 분리를 통해, 각 컴포넌트는 `컴포넌트 본연의 역할에 집중`
- `프레젠테이션 컴포넌트`
	- 단순히 `데이터 수정 없이 표시하는 역할`만 수행하므로, `재사용이 용이`
	- *여러 곳에서 재사용될 경우*, 컴포넌트에 수정사항을 적용하면 해당 `변경 사항`이 애플리케이션 전반에 `일관되게 적용`
	- 보통 순수 함수인 경우가 많으므로, `테스트 용이`
### 단점
- `React Hooks`로 충분히 동일한 결과를 얻을 수 있고, 더 명료함
## 고차 컴포넌트(HOC) 패턴
```js
// a.js
function HOC(Component, url) {
	return (props) => {
		// do something like API Communication
		const [fetchedData, setFetchedData] = useState();
		useEffect(()=>{
			fetch(...).then(res=>setFetchedData(res));
		}, []);
		return <Component {...props} data={fetchedData}/>
	}
}

// b.js
function Display(props) {
	return HOC(props.component, props.url);
}
```
`고차 컴포넌트 패턴`이란, 여러 컴포넌트에서 **`동일한 로직`을 `재사용`** 하기 위한 방법 중 하나로, `컴포넌트`를 `인자`로 받아 특정 `로직을 포함시킨 컴포넌트`를 `반환`하는 `고차 컴포넌트(HOC)`를 사용하는 방법입니다.  
### 훅으로의 대체
```js
// 기존
function HOC(Component) {
	const clickHandler = (event) => { ... };
	return <Component onClick={clickHandler} />
}
	
// 훅으로 대체
function useHOC() {
	const ref = useRef(null);
	const clickHandler = (event) => { ... };
	useEffect(()=>{
		const node = ref.current;
		// ref 대상이 렌더링 된 경우 클릭 이벤트 핸들러 attach
		if(node) {
			node.addEventListener("click", clickHandler);
		}
		// 컴포넌트가 마운트 해제될 때 이벤트 리스너를 지우는 후처리
		return () => {
			node.removeEventListener("click", clickHandler);
		}
	}, []);
	return ref;
}
function Component(props) {
	const hocRef = useHOC();
	return <div ref={hocRef}> ... </div>
}
```
`고차 컴포넌트 패턴` 역시 `React Hook`으로 대체 가능합니다.  
다만, `훅`은 `Element`를 반환할 수 없으므로, 대신 `Ref(useRef를 통해 얻은)`를 반환하며, 기존에 `Element`에게 `props`로 직접 전달하던 것을 `ref`를 통해 `설정`하는 방식으로 구현 방법이 바뀌게 됩니다.  
위처럼 `훅`으로 `HOC`를 대체하는 방식은, `컴포넌트 트리의 깊이를 줄여` 복잡해지는 것을 방지할 수 있다는 `장점`이 존재합니다.  
다만, `훅`을 사용할 경우 해당 `훅`을 호출하는 각각의 컴포넌트에서 스스로만의 동작을 추가할 수 있으므로 `HOC`에 비해 `Hook 내부의 로직`을 `수정`할 경우 `버그가 발생할 위험`이 더 높다는 `단점`이 존재합니다.  
#### HOC를 사용하기 좋은 케이스
- 동일한, 사용자마다 `재정의될 수 없는 동작`을 `여러 컴포넌트`가 수행해야 하는 경우
- 추가적인 커스텀 로직 없이, `독립적으로 컴포넌트가 동작`할 수 있는 경우
#### 훅을 사용하기 좋은 케이스
- 이를 이용하는 각 컴포넌트마다 `동작이 각각에 맞게 수정`되어야 하는 경우
- 동작이 `한 두개의 컴포넌트에서 사용`되는 경우
### 장점
- 재사용하고자 하는 `로직`을 `한 곳에 모두 보관`할 수 있음
### 단점
```js
function HOC(Component) {
	return props => <Component style={ ... } {...props} />
}
// 이 경우, HOC가 전달하는 style 값으로 button에 전달하고 있던 style 값이 대체됨
const StyledComponent = HOC(() => <button style={ ... }>button</button> );
```
- `prop`간 `이름 충돌`이 발생할 수 있음
	- `HOC`와 `Component` 모두 특정 prop을 사용한다면, `HOC`에서 `Component`에게 전달한 prop 값으로 기존 `Component` 내부에서 정의되고 있던 prop 값이 덮어씌워짐
		- ex) `HOC`에서 `Component`에게 `style` prop 값을 전달하고, `Component` 함수 내부에서도 `style` prop 값을 지정하고 있는 경우
```jsx
function withStyles(Component) {
  return props => {
    const style = { padding: '0.2rem', margin: '1rem' }
    return <Component style={style} {...props} />
  }
}

const Button = () = <button style={{ color: 'red' }}>Click me!</button>
const StyledButton = withStyles(Button)
```
## 렌더 프롭스(Render Props) 패턴
```jsx
const Title = (props) => props.render();

<Title render={() => <h1>I am a render prop!</h1>} />
```
`렌더 프롭스 패턴`이란 `컴포넌트 재사용성`을 높이기 위한 방법 중 하나로, `JSX 요소`를 반환하는 `함수`를 `render` 라는 `prop`으로 전달하고 컴포넌트에서 이를 사용하는 방식을 말합니다.  
컴포넌트는 `render` prop으로 전달된 것 외에는 렌더링하지 않습니다.  
```jsx
function Component(props) {
  const data = { ... }

  return props.render(data);
}

<Component render={data => <ChildComponent data={data} />}
```
대신 `render`에게 전달할 특정할 데이터를 불러오거나 만들어내는 로직을 주로 수행합니다.  
참고로 `render`라는 prop 이름을 사용하는 것은 일종의 `관례상 사용`하는 것으로, 다른 이름으로 전달해도 동일한 동작을 수행할 수 있습니다.  
### 상태 끌어올리기
```jsx
<Parent>
	<SiblingInput value={value} handleChange={handleChange} />
	<SiblingView value={value} />
</Parent>
```
위처럼 형제 컴포넌트간 상태값을 공유해야하는 경우, 위처럼 구현할 수도 있지만 `render` prop을 사용해 아래와 같이 구현할 수도 있습니다.  
```jsx
function Input(props) {
	const [input, setInput] = useState();
	return (
		<>
			<input value={value} onChange={(e)=>setInput(e.target.value)} />
			// render로 전달될 JSX 함수에게 value를 인자로 전달
			{props.render(value)}
		</>
	);
}

function Parent() {
	// 인자로 전달받은 value 값을 하위 SiblingView 컴포넌트들에게 전달하는 render JSX 함수
	return <Input render={(value) => (
			<>
				<SiblingView value={value} />
				<SiblingView2 value={value} />
			</>
		)} 
	/>
}
```
### props.children을 함수로 활용하기
```jsx
function Parent() {
  return (
	  <Input>
		{(value) => (
		  <>
			<Kelvin value={value} />
			<Fahrenheit value={value} />
		  </>
		)}
	  </Input>
  );
}

function Input(props) {
	const [input, setInput] = useState();
	return (
		<>
			<input value={value} onChange={(e)=>setInput(e.target.value)} />
			// Input의 자식 컴포넌트로 전달될 JSX 함수에게 value를 인자로 전달
			{props.children(value)}
		</>
	);
}
```
`render` prop으로 JSX 함수를 전달할 수도 있지만, 컴포넌트의 자식으로 JSX 함수를 전달할 수도 있습니다.   
이는 `children` prop이 사실상 `render prop`과 유사하게 동작하기 때문에 가능합니다.   
- [Passing Props to a Component – React](https://react.dev/learn/passing-props-to-a-component#passing-jsx-as-children)
### 훅으로 대체하기
```jsx
function Parent() {
	const [input, setInput] = useState();
	return (
		<>
			<input value={input} onChange={(e) => setInput(e.target.value)} />
			<Kelvin value={value} />
			<Fahrenheit value={value} />
		</>
	);
}
```
`Render Props` 패턴 역시 `React Hook`을 통해 대체할 수 있습니다.  
`render` 함수 인자로 값을 전달하는 대신, 위처럼 상태와 로직을 부모 컴포넌트에서 관리하면 됩니다.  
다만 이 경우 `Parent`에서 상태가 관리되므로 이를 사용하지 않는 자식 컴포넌트들까지 재렌더링 될 수 있기 때문에 `React.memo`와 같은 기능을 통해 불필요한 재렌더링을 방지해주어야 합니다.  
### 장점
- 여러 컴포넌트 간 로직 및 데이터 공유가 쉬워진다.  
	- 이는 `HOC 패턴`과도 비슷하지만, `렌더 프롭스 패턴`은 `HOC 패턴`의 몇 가지 단점을 보완함.
		- `props`를 자동 병합하지 않으므로, `HOC 패턴`에서 발생하던 `이름 충돌 문제`가 발생하 않음.
		- `props`를 `명시적`으로 전달하므로, `HOC 패턴`의 `암시적 props` 문제가 발생하지 않음.
			- 요소에 전달 될 `props`는 모두 `render prop`의 인수 목록에 명시됨.
- 렌더링 컴포넌트로부터 로직을 분리해낼 수 있다.
	- 하나의 컴포넌트를 `Stateful 컴포넌트`와, `Stateful 컴포넌트`로 부터 데이터를 전달받아 렌더링하는 `Stateless 컴포넌트`로 분리할 수 있음.
## React Hooks 패턴
`React Hooks 패턴`이란, [React 16.8에 추가된 Hooks 기능](https://legacy.reactjs.org/docs/hooks-intro.html)을 활용해 ***`상태`** 를 필요로 하는* `로직`을 `여러 컴포넌트에서 재사용`하는 패턴을 말합니다.
### Hooks 등장 이전
```jsx
class Component extends React.Component {
  constructor() {
    super()
	// 상태 선언
    this.state = { ... }
	// 클래스 컴포넌트에 사용자 정의 함수를 추가하는 경우
	// 해당 함수에 this를 바인딩해주어야 함.
	// 그러지 않으면 해당 함수 내부에서 호출하는 this는 undefined가 되거나
	// 해당 함수를 사용하는 HTML 요소 등이 this로 지정될 수 있다.
    this.customMethod = this.customMethodOne.bind(this)
  }

  /* 렌더링 생명주기 관련 메서드 */
  componentDidMount() { ...}
  componentWillUnmount() { ... }

  /* 사용자 정의 함수 */
  customMethod() { ... }

  render() { return { ... }}
}
```
`React`에 훅이 도입되기 전에는 상태, 렌더링 생명 주기 함수를 사용하려면 **반드시** `클래스 컴포넌트`를 사용해야 했습니다.  
`클래스 컴포넌트`는 *로직을 추가해 나갈 수록* `컴포넌트 크기가 증가`하고, `로직끼리 엉키고 구조화 불가능`해지면서 어떤 로직이 `어디서 호출`되는지 `추적`하기 `힘들어질 수` 있습니다.  
### Hooks
앞선 클래스 컴포넌트의 단점들을 해결하고자, `React Hooks`가 추가되었습니다.  
`React Hooks`는 `함수형 컴포넌트`가 상태를 다룰 수 있도록, *렌더링 생명주기 메서드 없이도* 렌더링 생명주기를 관리할 수 있도록, `상태를 필요로 하는 로직`을 `재사용`할 수 있도록 해줍니다.  
#### 상태 다루기 -> `useState()`
```jsx
	const [stateSnapshot, setState] = useState(initialValue);
```
`React`에서 제공하는 `useState(초기값)`훅을 통해 `상태의 현재 스냅샷`, `상태를 업데이트하는 함수`를 얻어낼 수 있으며 이를 통해 함수형 컴포넌트 내부에서 상태를 관리할 수 있습니다.  
#### 렌더링 생명주기 다루기 -> `useEffect()`
```jsx
// 컴포넌트가 마운트 되었을 때
componentDidMount() { ... }
useEffect(() => { ... }, [])

// 컴포넌트가 업데이트 되었을 때
componentDidUpdate() { ... }
useEffect(() => { ... })

// 컴포넌트가 마운트 해제 되었을 때
componentWillUnmount() { ... }
useEffect(() => { return () => { ... } }, [])

```
`useEffect()` 훅을 사용해 컴포넌트가 마운트 되었을 때(`componentDidMount()`), 컴포넌트가 업데이트 되었을 때(`componentDidUpdate()`), 컴포넌트가 마운트 해제되었을 때(`componentWillUnmount()`) 수행할 동작을 지정할 수 있습니다.  
#### 상태를 필요로 하는 로직 분리하기 -> Custom Hooks
```jsx
// 예시
// 인자로 넘어온 targetKey가 눌린 경우,
// 상태 값인 keyPressed를 true로 만들고, 키를 떼면 false로 만드는 훅이다.
function useKeyPress(targetKey) {
  const [keyPressed, setKeyPressed] = React.useState(false)

  function handleDown({ key }) {
    if (key === targetKey) {
      setKeyPressed(true)
    }
  }

  function handleUp({ key }) {
    if (key === targetKey) {
      setKeyPressed(false)
    }
  }

  React.useEffect(() => {
    window.addEventListener('keydown', handleDown)
    window.addEventListener('keyup', handleUp)

    return () => {
      window.removeEventListener('keydown', handleDown)
      window.removeEventListener('keyup', handleUp)
    }
  }, [])

  return keyPressed
}
```
`React`에서 제공하는 여러 `빌트-인 훅`을 사용해 `사용자 정의 훅`을 만들 수 있습니다.  
모든 훅은 [Hooks의 규칙](https://react.dev/reference/rules/rules-of-hooks)에 따라 `use`로 시작하는 이름을 가져야 합니다.  
#### 이외의 훅
위에서 다룬 훅 이외에도, `React`에는 다음과 같은 `빌트-인 훅`이 있습니다.  
##### useContext
`context 객체(React.createcontext의 반환값)`를 받아, 해당 컨텍스트의 `현재 컨텍스트 값을 반환`하는 훅입니다.  
`useContext(컨텍스트 객체)`만 호출하면 어느 컴포넌트에서든 해당 값에 접근할 수 있습니다.  
##### useReducer
```jsx
// 리듀서 함수
function reducer(state, action) {
  switch (action.type) {
    case 'incremented_age': {
      return {
        name: state.name,
        age: state.age + 1
      };
    }
    case 'changed_name': {
      return {
        name: action.nextName,
        age: state.age
      };
    }
  }
  throw Error('Unknown action: ' + action.type);
}
// 컴포넌트 내부에서 dispatch 호출
...
	const [state, dispatch] = useReducer(reducer, { name: 'Taylor', age: 42 });
	// 디스패치 함수의 인자로 넘긴 객체는 리듀서 함수의 action으로 넘겨진다
    dispatch({
      type: 'changed_name',
      nextName: e.target.value
    });
...
```
`setState()` 함수의 대안으로 다음 상태값이 `이전 상태값에 의존`하거나, 여러 다른 값을 포함해 계산 해야하는 복잡한 로직이 있는 경우 주로 사용됩니다.  
`useReducer` 훅은 `리듀서 함수`와 `초기 상태 값`을 입력받아, `현재 상태 값`과 `dispatch 함수`를 반환합니다.  
`리듀서 함수`는 입력받은 `action`에 따라 `상태 값`을 변화시키며, `dispatch 함수`는 리듀서 함수의 `어떤 action`을 호출할건지, 어떤 값을 넘길건지 지정해 호출합니다.  
## 컴파운드 패턴
```jsx
// 예제 (출처: [Compound Pattern (patterns.dev)](https://www.patterns.dev/react/compound-pattern))
// 이미지에 마우스를 hover하면 나타나는 ... 버튼과
// 이를 클릭시 나타나는 Flyout 메뉴의 Edit, Delete 버튼을 보여주기 위한 메뉴

// Flyout.jsx
const FlyOutContext = createContext();

function FlyOut(props) {
  const [open, toggle] = useState(false);

  return (
    <FlyOutContext.Provider value={{ open, toggle }}>
      {props.children}
    </FlyOutContext.Provider>
  );
}

function Toggle() {
  const { open, toggle } = useContext(FlyOutContext);

  return (
    <div onClick={() => toggle(!open)}>
      <Icon />
    </div>
  );
}

function List({ children }) {
  const { open } = useContext(FlyOutContext);
  return open && <ul>{children}</ul>;
}

function Item({ children }) {
  return <li>{children}</li>;
}

// 아래와 같이 선언할 경우, Toggle, List, Item은 FlyOut의 Static Property가 된다.
// 따라서, 이들을 사용하는 쪽에서는 FlyOut만 Import해도 이들을 사용할 수 있다.
FlyOut.Toggle = Toggle;
FlyOut.List = List;
FlyOut.Item = Item;

// FlyoutMenu.jsx
import React from "react";
import { FlyOut } from "./FlyOut";

// FlyoutMenu에는 아무런 상태를 추가하지 않고,
// FlyOut 메뉴를 구현할 수 있다.
export default function FlyoutMenu() {
  return (
    <FlyOut>
      <FlyOut.Toggle />
      <FlyOut.List>
        <FlyOut.Item>Edit</FlyOut.Item>
        <FlyOut.Item>Delete</FlyOut.Item>
      </FlyOut.List>
    </FlyOut>
  );
}
```
`컴파운드(Compound) 패턴`이란, 하나의 작업을 위해 여러 컴포넌트들을 만들어 역할을 분담시키는 패턴을 말합니다.  
이들은 상태를 공유하며 서로에게 의존하고 로직을 공유하는데, 예시로 `select` 입력창, 드롭다운 메뉴과 같은 것을 들 수 있습니다.  
### Context API
이들은 서로의 상태에 의존하며 동작을 수행하므로, `Context API`와 같이 값을 공유할 수 있는 매개체가 필요합니다.  
### `React.Children.map`
```jsx
export function FlyOut(props) {
  const [open, toggle] = React.useState(false);

  return (
    <div>
      {React.Children.map(props.children, (child) =>
        React.cloneElement(child, { open, toggle })
      )}
    </div>
  );
}
```
`컴파운드 패턴`은 컴포넌트의 자식들을 `순회 처리(mapping)`하면서도 사용할 수 있다.  
위 예시에서는 `open`, `toggle` 프로퍼티를 자식 에게 추가하기 위해, 자식들을 추가 prop과 함께 복제한다.  
즉, *`Context API`를 사용한 이전 예제*와 달리, 이 예제는 `props`를 통해 자식들에게 `open`, `toggle`을 전달한다.  
### 장점
- `컴파운드 컴포넌트`를 `구현(implement)`시, 별도로 `상태 관리`에 대해 `걱정할 필요가 없음`.  
	- 이들은 자체적으로 내부 상태를 관리하며, 자식 컴포넌트들과 공유하기 때문
- `컴파운드 컴포넌트`를 이루는 **하위 컴포넌트**들을 `명시적으로 import`할 `필요 없음`
	- 정적 프로퍼티로 하위 컴포넌트들을 지정해놓기 때문
### 단점
- `React.Children.map` 관련 단점
	- 컴포넌트의 깊이가 제한됨.
		- **`직계 자식들`** 만 부모 컴포넌트의 `open`, `toggle`과 같은 `prop`을 `접근`할 수 있음.
		- 따라서 이들을 다른 컴포넌트로 더 감쌀 수 없음.
```jsx
export default function FlyoutMenu() {
  return (
    <FlyOut>
      {/* 부모-자식 관계 끊어짐 */}
      <div>
        <FlyOut.Toggle />
        <FlyOut.List>
          <FlyOut.Item>Edit</FlyOut.Item>
          <FlyOut.Item>Delete</FlyOut.Item>
        </FlyOut.List>
      </div>
    </FlyOut>
  );
}
```
- `React.cloneElement` 관련 단점
	- `props` 이름충돌이 발생할 수 있음.
		- `React.cloneElement`로 요소 복사시, 얕은 병합이 이뤄짐
		- 따라서 이미 존재하는 프로퍼티가 `React.cloneElement`에 전달한 프로퍼티와 이름이 같으면, 전달하는 값으로 덮어씌워짐
# 참고
- [Patterns.dev](https://www.patterns.dev/#patterns)
	- [Overview of React.js (patterns.dev)](https://www.patterns.dev/react)
	- [Container/Presentational Pattern (patterns.dev)](https://www.patterns.dev/react/presentational-container-pattern)
	- [HOC Pattern (patterns.dev)](https://www.patterns.dev/react/hoc-pattern)
	- [Render Props Pattern (patterns.dev)](https://www.patterns.dev/react/render-props-pattern)
	- [Hooks Pattern (patterns.dev)](https://www.patterns.dev/react/hooks-pattern)
	- [Compound Pattern (patterns.dev)](https://www.patterns.dev/react/compound-pattern)