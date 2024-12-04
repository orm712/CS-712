# 타입스크립트?
- 마이크로소프트에서 2012년 처음 개발한 Javascript의 `슈퍼셋 언어`
- 자바스크립트에 `선택적 타입 애너테이션(타입 힌트)`가 있는 `정적 타입`을 추가하기 위해 개발됨
- 클라이언트 및 서버 측(ex. `Node.js`, `Deno`와 같은 런타임) 애플리케이션을 개발하는 것 모두 지원
- 궁극적으로 자바스크립트로 `트랜스파일`되며, 이를 위해 기본 타입스크립트 컴파일러나 `Babel`과 같은 트랜스파일러를 사용
	- 타입 및 실행 코드가 포함된 `구현 파일`(`*.ts`)을 작성하면, 출력으로 `.js` 파일을 생성
- C++의 헤더 파일처럼, 타입 정보만 포함하는 `선언(declaration) 파일`(`*.d.ts`)을 지원함.
	- `선언 파일`은 별도의 `.js` 파일을 생성해내지 않음
	- `React`, `D3.js` 등 자바스크립트에서 사용되는 서드파티 라이브러리들 중 타입스크립트를 지원하는 라이브러리들은 [DefinitelyTyped Repository](https://github.com/DefinitelyTyped/DefinitelyTyped/)에 `선언 파일`들을 저장해두고 `@types/라이브러리명` 의 형태로 npm 을 통해 설치할 수 있게 함
## 개발 이유 - [TypeScript: Why does TypeScript exist?](https://www.typescriptlang.org/why-create-typescript/)
### 개발 안정성
- 자바스크립트는 `동적 타입 언어`이므로, 하나의 변수에 `다양한 자료형의 값`들을 `할당`할 수 있음
- 따라서 한 부분에서 코드를 수정해도, `다른 부분에서 이를 파악하기 힘듦`
	- 예를 들어, 문자열을 받아 소문자로 바꾸는 함수에 문자열 대신 숫자를 넘겨도 `실행은 정상적으로 진행`됨
- 이는 몇 백, 몇 천개의 변수들이 사용되는 큰 프로젝트의 경우 `변수의 자료형들을 추적`하기 `매우 힘들게 함`
- 반면 `타입스크립트`를 사용하면 이러한 코드들 간의 연결이 유효한지 `컴파일 시간에 자동으로 검사`해주므로 `시간을 절약`할 수 있고 `신뢰할 수 있는 코드`를 작성하게 해줌
### 간단해진 타입 검사
- 기존의 경우, 자바스크립트 함수 같은 곳에서 `특정 자료형의 인자`만 받고싶을 경우 `타입 검사`를 해야 했음
	- 이를 위해 `typeof` 연산자를 사용하거나, Object의 `키-값`들을 검사해야 했음
	- 이는 번거롭고, 해당 함수를 호출하는 부분에서는 어떤 자료형의 값을 넘겨야 하는지 함수 코드를 뜯어보지 않는 이상 알 수 없었음
- 반면 타입스크립트에서는 함수 인자에 특정 자료형을 지정할 수 있고, 올바르지 않은 자료형의 값을 인자로 넘길 경우 런타임 시간이 오류를 통해 알려줌
### 코드 가독성
- 자바스크립트에서는 하나의 변수가 현재 어떤 자료형의 데이터를 갖고있는지 알아내려면 이전 코드 흐름들을 추적해야 함
- 반면 타입스크립트에서는 변수가 선언될 때의 자료형에 해당하는 값만 가질 수 있으므로 어떤 자료형일지 신경쓰지 않아도 됨
## 주요 기능
### 정적 타입 검사 [#](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)
```ts
// 원시 타입 변수에 대한 타입 검사
let firstname: string = "kim";
firstname = 10; // Type 'number' is not assignable to type 'string'

// 객체 타입 변수에 대한 타입 검사
let sportsCar: {
    name: string,
    isGasoline: boolean
} = {
    name: "Porshe",
    isGasoline: false,
};

// 함수 인자에 대한 타입 검사
function add(x: number, y: number) {
    return x + y;
}
add(10, 20); // 30
add(10, "인자"); // Argument of type 'string' is not assignable to parameter of type 'number'

// 함수 반환값에 대한 인자 타입
function test(x: number): number {
    return 10 + x;
}
```
- 타입스크립트에서는 변수, 객체의 프로퍼티, 함수 인자 및 반환 값 등 다양한 부분에 `타입 애니테이션`을 통해 타입을 지정할 수 있음
- 그리고 이들을 컴파일 시간에 정적으로 코드의 타입들을 분석함
- 따라서 변수에 올바르지 않은 자료형의 값을 할당하거나, 함수에 올바르지 않은 자료형의 값을 넘길 경우 컴파일 시간 에러가 발생함
#### 타입 추론 [#](https://www.typescriptlang.org/docs/handbook/type-inference.html)
```ts
let x = 3;
x = 30; // x: number
```
- 또한 타입스크립트는 별도로 타입을 명시하지 않아도, 리터럴 값이 할당되어 있다면 해당 값을 기준으로 타입을 추론함
#### 타입 별칭(Type Alias)
```ts
// 특정 타입에 대해 별칭을 붙여 사용할 수 있음
type Point = {
  x: number;
  y: number;
};
 
function printCoord(pt: Point) {
	console.log("좌표의 x값은 " + pt.x);
	console.log("좌표의 y값은" + pt.y);
}
 
printCoord({ x: 100, y: 100 });
```
- 타입스크립트에서는 특정 타입에 대해 별칭을 붙여 사용할 수 있음
- 이는 반복되는 객체 타입 애너테이션과 같이 동일한 유형이 여러 차례 반복되는 경우 유용함
### 인터페이스 [#](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#interfaces)
```ts
interface Point = {
  x: number;
  y: number;
};
 
function printCoord(pt: Point) {
	console.log("좌표의 x값은 " + pt.x);
	console.log("좌표의 y값은" + pt.y);
}
 
printCoord({ x: 100, y: 100 });
```
- 타입스크립트에는 타입 별칭과 더불어 객체 타입의 이름을 명명할 수 있는 `인터페이스`가 존재
- 기존의 `타입 별칭`과 유사하지만, 새로운 속성을 추가할 수 있다는 점이 다름
```ts
// 인터페이스의 속성 추가
interface Window {
	title: string;
}

interface Window {
	ts: TypeScriptAPI;
}

const src = 'const a = "Hello World"';
window.ts.transpileModule(src, {});

// 타입 별칭의 속성 추가는 불가능
type Window = {
	title: string;
}

type Window = {
	ts: TypeScriptAPI;
}
 // Error: Duplicate identifier 'Window'
```

### 클래스 기반 객체 [#](https://www.typescriptlang.org/docs/handbook/2/classes.html#handbook-content)
```ts
class Point {
	public x: number;
	private y: number;
	constructor(x: number, y: number) {
		// 클래스 프로퍼티수에 값을 할당
		this.x = x;
		this.y = y;
	}
	scale(n: number): void {
		this.x *= n;
		this.y *= n;
	}
}
 
const pt = new Point(10, 20);
```
- 다른 객체지향 언어처럼 자바스크립트 클래스의 프로퍼티와 메서드에 타입을 지정할 수 있게 해줌
- 또한, 자바스크립트와 달리 접근 제한자를 지원해  다른 객체지향 언어에서 지원하는 `public`, `protected`, `private`와 같은 접근 제한자를 지원함
### Enum [#](https://www.typescriptlang.org/docs/handbook/enums.html#handbook-content)
```ts
// UP이 1로 초기화되어있으므로, 이어지는 멤버들은 모두 auto-increment의 형태로 증가함
// 또한 `= 1`을 생략할 수도 있는데, 이 경우 Up은 0으로 초기화됨
enum Direction {
	Up = 1,
	Down,
	Left,
	Right,
}

// 또는 아래와 같이 문자열 기반의 Enum도 가능함
// 이 경우 모든 멤버들은 문자열 리터럴이나 다른 문자열 Enum 멤버 값으로 초기화되어야 함
enum Direction {
	Up = "UP",
	Down = "DOWN",
	Left = "LEFT",
	Right = "RIGHT",
}
```
- 타입 확장이 아닌 타입스크립트만의 고유한 기능으로, 명명된 상수 집합을 선언할 수 있음
- 숫자 및 문자열 기반 Enum 모두 가능
### 제네릭 [#](https://www.typescriptlang.org/docs/handbook/2/generics.html#handbook-content)
```ts
// 함수에서의 제네릭
function identity<Type>(arg: Type): Type {
  return arg;
}

// 1. 명시적으로 <>를 통해 타입 설정
let output = identity<string>("myString"); // output: string

// 2. 타입 추론을 통한 타입 설정(권장되진 않음)
let output = identity("myString"); // output: string

// 클래스에서의 제네릭
class GenericNumber<NumType> {
  zeroValue: NumType;
  add: (x: NumType, y: NumType) => NumType;
}
 
let myGenericNumber = new GenericNumber<number>();
```
- 타입스크립트에도 데이터 타입을 일반화할 수 있는 `제네릭`이 존재
	- 클래스 또는 메서드에서 어떤 타입을 사용할 지를 컴파일 시간에 미리 지정하는 것
	
### 타입 가드 [#](https://www.typescriptlang.org/docs/handbook/advanced-types.html#user-defined-type-guards)
```ts
// typeof
function printAll(strs: string | string[] | null) {
	if (typeof strs === "string") {
		// 이 시점에서 strs는 string
		console.log(strs);
	} else {
		// do nothing
	}
}

// 사용자 지정 타입 가드
function isFish(pet: Fish | Bird): pet is Fish {
	return (pet as Fish).swim !== undefined;
}

let pet = getSmallPet();
 
if (isFish(pet)) { 
	// 타입 가드를 통과했으므로 pet은 Fish
	pet.swim();
} else {
	// pet은 Fish | Bird 인데 Fish가 아니므로 Bird 일 수 밖에 없다는 것을 유추
	pet.fly();
}
```
- 타입스크립트에서 변수의 자료형 범위를 좁히는 검사 방식을 `타입 가드`라고 함
- 이는 `typeof`, `instanceof`와 같은 연산자는 물론 `==`와 같은 연산자를 통한 추론도 이뤄짐
- 또한 `사용자 지정 타입 가드`도 사용 가능
	- `A is/is not B`라는 `타입 술어(type predicate)`를 사용
		- `술어`란, "A는 B다" 또는 "A는 B가 아니다"라고 할 때 B를 술어라고 함
### 유니온 타입 [#](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes-func.html#unions)
```ts
function printId(id: number | string) {
	console.log("Your ID is: " + id);
	if(typeof id === "string") {
		// 이 지점에서 id는 string임
		console.log(id.toUpperCase());
	} else {
		// 이 지점에서 id는 number임
	}
}
// OK
printId(101);
// OK
printId("202");
// Error
printId({ myId: 22342 }); // Argument of type '{ myID: number; }' is not assignable to parameter of type 'string | number'.
```
- 타입스크립트는 여러 타입들과 연산자를 활용해 새로운 타입을 만들어 낼 수 있음
- 이러한 유니온 타입이 지정된 변수는 유니온에 포함된 모든 멤버들에 유효한 연산만 허용함
	- 즉, 위 예시처럼 `number`, `string` 유니온 타입인 경우 `string`에서만 사용할 수 있는 `toUpperCase`와 같은 메서드는 사용할 수 없음 
	- 이처럼 유니온에 포함된 타입 중 특정 타입에만 존재하는 기능을 사용하고 싶다면 해당 타입으로 타입을 좁혀야 함
### 옵셔널 프로퍼티 [#](https://www.typescriptlang.org/docs/handbook/2/objects.html#optional-properties)
```ts
function printName(obj: { first: string; last?: string }) {
	// last는 옵셔널 프로퍼티이므로 string | undefined로 추론됨
	// 따라서 'obj.last' is possibly 'undefined'. 라는 에러가 발생
	console.log(obj.last.toUpperCase());

	// 'obj.last' is possibly 'undefined'.
	if (obj.last !== undefined) {
		// 하지만 이렇게 타입 가드를 통해 타입을 좁히면 string 타입으로 추론
		console.log(obj.last.toUpperCase());
	}
	
	// 또는 최신 자바스크립트 문법인 옵셔널 체이닝 문법을 사용해 접근할 수도 있음
	// 다만 이 경우에는 정상적인 동작을 하지 않을 수도 있음
	console.log(obj.last?.toUpperCase());
}
// Both OK
printName({ first: "Bob" });
printName({ first: "Alice", last: "Alisson" });
```
- 타입스크립트의 객체 타입의 경우, 일부 또는 모든 프로퍼티를 옵셔널로 지정할 수 있음
- 따라서 이들을 사용하려 하면 타입 가드를 활용해 타입을 좁히는 것이 필요함
### 데코레이터 [#](https://www.typescriptlang.org/docs/handbook/decorators.html#handbook-content)
```ts
function first() {
// 데코레이터 팩토리 함수
  console.log("first(): factory evaluated");
  return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
	// 이 부분이 실제 데코레이터
	console.log("first(): called");
  };
}
 
function second() {
  console.log("second(): factory evaluated");
  return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
	console.log("second(): called");
  };
}
 
class ExampleClass {
  @first()
  @second()
  method() {}
}
// 실행 순서
// first(): factory evaluated
// second(): factory evaluated
// second(): called
// first(): called
```
- 타입스크립트는 `클래스 선언`, `메서드`, `접근자`, `프로퍼티`, `매개변수`에 붙일 수 있는 `데코레이터` 기능을 지원
	- `데코레이터`는 함수의 형태로 표현되야 함. 즉 `@deco` 라면 `deco` 라는 함수가 런타임에 존재하고 있어야 함
	- `데코레이터`는 두개 이상 달릴 수 있으며, 이때 평가는 위에서부터 아래로, 호출은 아래서부터 위로 이루어짐
	- 어디에 달리느냐에 따라, 어떤 값이 인자로 주어질 지 다름
		- `클래스`에 달릴 경우, 해당 클래스의 `constructor`가 인자로 주어짐
		- `메서드`, `접근자`에 달릴 경우, 다음 3가지 인자를 받음
			- `constructor`(static 멤버인 경우) 또는 `클래스의 prototype`(instance 멤버인 경우)
			- 멤버의 `이름`
			- 멤버의 `속성 설명자(Property Descriptor)`
		- `프로퍼티`에 달릴 경우, 다음 2가지 인자를 받음
			- `constructor`(static 멤버인 경우) 또는 `클래스의 prototype`(instance 멤버인 경우)
			- 멤버의 `이름`
		- `매개변수`에 달릴 경우, 다음 3가지 인자를 받음
			- `constructor`(static 멤버인 경우) 또는 `클래스의 prototype`(instance 멤버인 경우)
			- 멤버의 `이름`
			- 함수의 매개변수 목록 중, 현재 매개변수가 갖는 `서수(첫 번째, 두 번째, ...) 인덱스`
# 참고
- [TypeScript: Handbook - Enums](https://www.typescriptlang.org/docs/handbook/enums.html#handbook-content)
- [TypeScript: Documentation - Advanced Types](https://www.typescriptlang.org/docs/handbook/advanced-types.html#user-defined-type-guards)
- [TypeScript: Documentation - Generics](https://www.typescriptlang.org/docs/handbook/2/generics.html#handbook-content)
- [TypeScript: Documentation - Everyday Types](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#interfaces)
- [TypeScript: Documentation - Classes](https://www.typescriptlang.org/docs/handbook/2/classes.html#handbook-content)
- [TypeScript: Handbook - Enums](https://www.typescriptlang.org/docs/handbook/enums.html#handbook-content)
- [TypeScript: Documentation - Type Inference](https://www.typescriptlang.org/docs/handbook/type-inference.html)
- [TypeScript: Documentation - Object Types](https://www.typescriptlang.org/docs/handbook/2/objects.html#optional-properties)
- [TypeScript: Documentation - Decorators](https://www.typescriptlang.org/docs/handbook/decorators.html#handbook-content)
- [TypeScript: Documentation - TypeScript for Functional Programmers](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes-func.html#unions)
- [TypeScript: Documentation - Type Inference](https://www.typescriptlang.org/docs/handbook/type-inference.html)