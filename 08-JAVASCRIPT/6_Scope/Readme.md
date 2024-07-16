## 스코프?
프로그래밍에서 `스코프`란, 프로그램의 요소들(변수 및 상수, 함수 등)이 유효한 영역을 말합니다. 또한, `스코프`는 프로그램의 부분 또는 특정 지점에서 유효한 모든 `이름 바인딩` 집합을 가리키는 데에도 사용되며, 이때 `컨텍스트` 또는 `환경(Environment)` 라고도 일컬어집니다.  
스코프 개념에 따라 프로그램에서 영역을 벗어난 변수는 가리킬 수 없고, 영역 내부의 요소들은 바인딩 된 `이름(identifier)`을 통해 가리킬 수 있게 됩니다.  
이는 스코프가 *프로그램의 다른 부분에 존재하는 변수*를 **서로 구분**하기 위해 등장했기 때문입니다.  
스코프 개념은 1960년 등장한 [`알골 60(ALGOL 60)`](https://www.algol60.org/)에서 처음 등장했으며, `블록`이라는 영역 단위를 통해 변수의 스코프를 지정했습니다.  

### 스코프의 종류
스코프는 *어디에서 변수를 참조하느냐*에 따라 `정적 범위(Static Scope)`와 `동적 범위(Dynamic Scope)` 두 가지로 나뉩니다.  
C, C++, Java와 같은 대부분의 현대 언어들은 `정적 범위` 방식을 채택하고 있으며, `LISP`, `TeX`등의 스크립팅 언어에서는 `동적 범위` 방식을 채택하기도 합니다.  

#### `정적 범위(Static Scope)`
`정적 범위`, 또는 `렉시컬 스코프(Lexical Scope)`라고도 부르는 이 방식에서는 소스코드의 위치와, 변수 또는 함수가 정의된(선언된) 위치에 의해 정의된 `렉시컬 컨텍스트(또는 정적 컨텍스트)`에 따라 참조가 정해집니다.  
이름을 탐색할 때, 먼저 로컬 `렉시컬 컨텍스트`를 검색하고, 실패하면 외부 `렉시컬 컨텍스트`로 점차 검색해나가는 식으로 탐색을 진행합니다.   
주로 `블록` 단위로 묶어 스코프를 정의합니다. 따라서 *블록 내부에서 선언한 변수*는 **외부에서 보이지 않으며**, *블록 외부의 변수*는 (*블록 내부에서 재정의하지 않는 한*)**블록 내부에서 볼 수 있습니다**.  

##### 예시
```c
#include<stdio.h>
int x = 10;
 
// g()에 의해 호출 됨
int f()
{
   return x;
}
 
// g()는 스스로 x라는 내부 변수를 갖고 있으며,
// f()를 호출함
int g()
{
   int x = 20;
   return f();
}
 
int main()
{
  printf("%d", g()); // "10"
  printf("\n");
  return 0;
}
```
`정적 범위`에서는 컴파일러가 먼저 선언된 현재의 블록을 탐색하고, 이후 이를 감싸는 더 넓은 범위로 계속해서 탐색해 변수를 검색합니다.  
따라서 위 코드에서는 `g()`가 호출한 `f()`는 내부 -> 외부(전역)에서 x 값을 탐색하게 되고, `10`을 출력하게 됩니다.  

#### `동적 범위(Dynamic Scope)`
`동적 범위` 방식에서는 실행 컨텍스트, 즉 함수 호출 순서에 따른 컨텍스트에 의해 참조가 정해지게 됩니다.  
이름을 탐색할 때, 먼저 `로컬 컨텍스트`에서 검색하고, 찾지 못하면 호출 스택에서 변수 정의를 검색해나가는 식으로 탐색을 진행합니다.  
**구현이 쉽지만**, *런타임에 어떤 변수에 접근할 수 있을지 알게되므로* **컴파일러가 최적화할 수 없어** `변수 조회 비용`이 **더 비싸고**, **함수 호출 순서에 따라 다르게 동작**할 수 있다는 단점(때로는 장점)이 있습니다.  

##### 예시
```c
int x = 10;
 
// g()에 의해 호출 됨
int f()
{
   return x;
}
 
// g()는 스스로 x라는 내부 변수를 갖고 있으며,
// f()를 호출함
int g()
{
   int x = 20;
   return f();
}
 
main()
{
  printf(g()); // "20"
}
```
`동적 범위`에서는 먼저 현재 블록에서 변수를 검색한 뒤, 호출 스택의 함수들을 연속적으로 검색합니다.  
따라서 위 코드에서 `g()`가 호출한 `f()`는 내부(`f()`) -> 이전 호출 함수(`g()`) 에서 x 값을 탐색하게 되고, `g()`에서 선언된 `20`이란 값을 출력하게 됩니다.  

### Javascript에서의 Scope
`자바스크립트`에서는 `전역 스코프`, `모듈 스코프`, `함수 스코프`, 그리고 추가로 `블록 스코프`까지 총 3가지의 스코 프가 존재합니다.  

#### `전역 스코프(Global Scope)`
`스크립트 모드`에서 실행되는 모든 코드들의 기본 범위.  
자바스크립트에서 *함수 영역 내부가 아닌* **전역에 선언**된 요소들은 모두 전역 스코프를 갖는 전역 변수로 취급됩니다. 또한 **`var` 키워드로 선언된 전역 변수**는 `전역 객체`인 `window`의 프로퍼티로 등록됩니다.  
그리고 *선언하지 않은 변수*에 **값을 할당**하려고 하면, 이는 자동적으로 `전역 변수`가 됩니다. 이를 `암묵적 전역(implicit global)`이라고 합니다. 참고로 `암묵적 전역`은 `"strict mode"` 에서 동작하지 않습니다.  
자바스크립트 프로그램 **어디에서나 접근할 수 있다**는 장점이 존재하지만, 남발할 경우 `변수 이름이 중복`될 수 있고, `재할당`되거나 내부 스코프에 의해 외부 스코프 변수가 가려지는 `변수 섀도잉(Variable Shadowing)`이 발생할 수 있다는 단점이 있습니다.  

##### 예시
```js
let global_let = 20; // 전역에 선언한 let 변수
var global_var = 30; // 전역에 선언한 var 변수
{
    var inner_var = 40; // 블록에 선언한 var 변수
}
function func() {
    console.log(global_let); // "20"
    console.log(global_var); // "30"
}
func();
console.log(inner_var); // "40" 
// var 키워드로 선언한 변수는 블록 스코프에 포함되지 않는다.

myFunction();  

console.log(carName); // "Volvo"
function myFunction() {  
  carName = "Volvo"; // 선언하지 않은 변수에 값을 할당할 경우 자동적으로 전역 변수가 된다.
}

```

#### `모듈 스코프(Module Scope)`
`모듈 모드`에서 실행되는 코드들의 범위.  
`전역 스코프`와 달리, 모듈에서 *함수 영역 외부에 선언된* `요소(변수, 함수 및 기타 코드)`는 **`명시적으로 내보내지(export)` 않는 한**, **숨겨져있으며** 다른 모듈에서 사용할 수 없습니다.  
그리고 모듈을 가져오게(`import()`, `require()` 등) 되면, `가져온 모듈`은 이를 **`가져온 코드의 스코프` 내부에서만 사용**할 수 있습니다. 즉, 만약 함수 내부에서 모듈을 가져올 경우, 가져온 모듈은 해당 함수 내에서만 접근할 수 있고 함수 외부에서는 접근할 수 없습니다.  

##### 예시
```js
// in abc.js
const var1 = 10;
function func() {
	...
}
...
export { func };

// in main.js
import { func } from "./abc";
func();
console.log(var1); // ReferenceError: var1 is not defined
```

#### `함수 스코프(Function Scope)`
함수로 생성된 범위.  
각각의 함수는 새로운 스코프를 생성하며, `함수의 매개변수` 및 `함수 내부에서 정의된 변수`는 **`함수 외부`에서 접근할 수 없습니다**. 이는 `var`, `let`, `const` 어떤 키워드로 선언했든 동일하게 적용됩니다.  
또한 `함수 내부에서 정의된 변수`와 `외부의 변수`의 이름이 동일해도 정상적으로 동작하며, 이때 해당 이름으로 접근하게 될 경우 `함수 내부에서 정의된 변수`를 우선적으로 접근합니다.  

##### 예시
```js
let global_let = 20; // 전역에 선언한 let 변수
var global_var = 30; // 전역에 선언한 var 변수

function func() {
	var func_var = 30; // 함수에 선언한 let 변수
	var global_var = 40; // 함수에 선언한 var 변수
    console.log(global_let); // "20"
    console.log(global_var); // "40" << 함수 내부에서 선언한 값을 우선
}
func();
console.log(func_let); // ReferenceError: func_var is not defined
console.log(global_var); // "30"
```

#### `블록 스코프(Block Scope)`
중괄호 쌍(블록)으로 만들어진 범위.  
아무것도 없는 중괄호 쌍으로 만들어진 블록 또는 `if`, `for`, `switch` 등의 블록 내부에서 선언된 변수를 해당 스코프에서만 사용 가능한 것을 말합니다.  
`ES6`에서 등장했으며, 마찬가지로 `ES6`에서 등장한 `let`, `const` 키워드로 선언된 변수들만 `블록 스코프`를 적용 받습니다.  

##### 예시
```js
{
	let abc = 10;
	var x = 20;
}
console.log(abc); // ReferenceError: abc is not defined
console.log(x); // 20

function func() {
	let abc = 20; // 함수 스코프에 선언된 변수
	if(abc === 20) {
		let abc = 30; // 블록 스코프에 선언된 변수
		console.log(abc); // 30
	}
	console.log(abc); // 20
}
func();
```

#### `변수 섀도잉(Variable Shadowing)`
`블록 스코프`, 또는 `함수 스코프`에서 발생가능한 현상으로, 동일한 이름을 갖는 내부 스코프의 변수와 외부 스코프의 변수를 접근할 경우 `내부 스코프`의 값이 우선되는 것을 말합니다.  
참고로 `변수 섀도잉`을 사용할 때, 외부-내부 변수들을 같은 유형으로 선언하는 것이 좋습니다. 특정 경우 충돌이 발생할 수 있기 때문입니다.  
아래 코드처럼, `var`로 선언한 외부 변수를 `let`으로 선언한 내부 변수로 가리는 것은 정상적 동작하지만, `let`으로 선언한 외부 변수를 `var`로 선언한 내부 변수로 가리려 하는 경우 "이미 정의된 변수"라는 오류가 발생하게 됩니다.  
```js
    var x = 'X';
    let y = 'Y';
    
    if (true) {
        let x = 'X'; // 정상 동작
        var y = 'Y'; // SyntaxError: Identifier 'y' has already been declared
        console.log(x); // "X"
        console.log(y); // 에러 출력
    }
```

#### `호이스팅(Hoisting)`
```js
// var 변수 호이스팅
console.log(x); // undefined
x = 20;

console.log(x); // 20
var x;

// 함수 호이스팅
// 함수를 이후에 선언해도, 앞에서 호출할 수 있다.
func(); // "TEST"

function func() {
	console.log("TEST");
}
// 다만, 함수를 변수에 할당한 경우, 선언-초기화가 분리되므로 함수 호출시 에러가 발생한다.

func(); // ReferenceError: func is not defined
const func = function() {
	console.log("TEST");
}
```
자바스크립트의 `호이스팅`이란, 인터프리터가 코드를 실행하기 전, `함수`/`변수`/`클래스`/`import` **선언**을 `현재 스코프 맨 위`로 이동시키는 과정을 말합니다.  
**`선언`** 만 끌어올린다는 것을 유의해야 하는데, `초기화(값 할당)`은 위로 끌어올려지지 않기 때문입니다.  
```js
x = "Test"; // ReferenceError: Cannot access 'x' before initialization
let x;

// 정상 케이스
let x;
x = "Test";
```

```js
// const는 애초에 선언시 함께 초기화가 이뤄져야 하므로 에러 발생
x = "Test"; // SyntaxError: Missing initializer in const declaration
const x;
```
또한, `let`과 `const`로 선언한 요소 및 `클래스`의 경우 `호이스팅`되긴 하지만, 선언 이전에 이 값들을 초기화할 수 없습니다.  
블록의 시작부터 이들이 선언 및 초기화가 된 위치에 도달하기 전까지의 구간을 **`일시적 데드 존(Temporal dead zone, TDZ)`** 이라고 하며, 해당 구간에서 변수에 접근하는 것을 엄격히 금하고 있습니다.  

# 참고
- [Scope (computer science) - Wikipedia](https://en.wikipedia.org/wiki/Scope_(computer_science)#Expression_scope)
- [CSE 341 Lecture Notes -- Static and Dynamic Scoping](https://courses.cs.washington.edu/courses/cse341/03wi/imperative/scoping.html)
- [CS 6110 S17 Lecture 12 Static vs Dynamic Scope - lec12.pdf](https://www.cs.cornell.edu/courses/cs6110/2017sp/lectures/lec12.pdf)
- [Static and Dynamic Scoping - GeeksforGeeks](https://www.geeksforgeeks.org/static-and-dynamic-scoping/)
- [Module Scope | Showwcase](https://www.showwcase.com/article/27294/module-scope)
- [An introduction to scope in JavaScript](https://www.freecodecamp.org/news/an-introduction-to-scope-in-javascript-cbd957022652/)
- [Variable Shadowing in JavaScript - GeeksforGeeks](https://www.geeksforgeeks.org/variable-shadowing-in-javascript/)
- [Module Scope | Showwcase](https://www.showwcase.com/article/27294/module-scope)
- [An introduction to scope in JavaScript](https://www.freecodecamp.org/news/an-introduction-to-scope-in-javascript-cbd957022652/)