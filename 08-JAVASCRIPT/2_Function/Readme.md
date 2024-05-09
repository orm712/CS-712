## Javascript의 `Function`에 대해
Javascript는 `일급 함수(first class function)`와 `람다(lambda)`를 지원하는 함수입니다.  
따라서, 함수를 다른 변수처럼 다룰 수 있고, 생성자 `Function()`를 통해 함수를 생성할 수 있습니다.  
`Function`의 prototype엔 빌트-인 메서드와 속성들이 존재하는데, 이에 대해 알아보고자 합니다.  
## `Function`의 built-in method
### `apply(thisArg, argsArray)`
```javascript
// thisArg를 넘긴 경우
const abc = { h: 30 }
function hh(abc) {
    return this.h + abc;
};
console.log(hh.apply(abc, [30]));
// 출력: 60

// thisArg를 넘기지 않은 경우
const numbers = [5, 6, 2, 3, 7];
const max = Math.max.apply(null, numbers);
console.log(max);
// Expected output: 7
```
주어진 `this` 값과 인수(array-like 형태의 객체)를 사용해 함수를 호출하고, 호출 결과를 반환합니다.  
#### 매개변수
 - `thisArg`: 함수를 호출하기 위한 `this` 값으로, 원시 값은 `Object` 로 변환되며 `null` 또는 `undefined`는 `strict mode`에서 전역 객체([`globalThis`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/globalThis))로 대체됩니다.
 - `argsArray`: 함수를 호출할 때 쓸 인수를 지정하는 **`array-like`** 객체입니다. 생략 가능합니다.  
	 - 여기서 말하는 `array-like` 객체란, 1) `length` 속성을 갖고 있고, 2) `0`~`length-1` 범위의 정수 index property가 있는 객체를 말합니다.
		 - 예를 들어 `{'length': 2, '0': 'abc', '1': 'abcd'}`와 같은 객체도 `array-like` 하다고 할 수 있습니다.  
	 - 이외에도 전달된 인수값을 함수 내부에서 접근 가능한 `array-like` 객체인 [`arguments`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions/arguments)도 사용 가능합니다.  
### `call(thisArg, arg1, arg2, ...)`
```javascript
function Product(name, year) {
	this.name = name;
	this.year = 2024;
}
function Food(name, year) {
	Product.call(this, name, year);
	this.type = "음식";
}
console.log(new Food('김치', '2024').name);
// 출력: "김치"
```
주어진 `this` 값과 1개 이상의 인수들을 사용해 함수를 호출하고, 호출 결과를 반환합니다.
#### 매개변수
 - `thisArg`: 함수를 호출하기 위한 `this` 값으로, 원시 값은 `Object` 로 변환되며 `null` 또는 `undefined`는 `strict mode`에서 전역 객체로 대체됩니다.  
 - `arg1, ..., argN`: 함수를 호출할 때 쓸 인수들입니다. 생략할 수 있습니다.  

### `apply()`와 `call()`의 이점
#### 메서드의 유틸리티 함수화
함수 내부에서 `this`를 사용하는 경우, 해당 함수를 객체의 `property`로 두고 사용해야 합니다.  
하지만, `apply()`나 `call()`을 사용하게 되면, 해당 함수를 `property`로 연결하지 않고 `thisArg`로 객체를 넘겨도 정상 동작하게 됩니다. 이는 객체의 `prototype`을 변경하지 않아도 됨을 의미합니다.  
즉, 특정 객체의 메서드를 유틸리티 함수처럼 사용할 수 있게 됩니다.  
```javascript
// array-like한 객체에서 map을 사용하는 방법
const callback = (a) => a+1;

// 기존의 map
[1].map(callback);
// [2]

// array-like 객체에 map 사용
Array.prototype.map.call({length: 1, '0': 'abc'}, callback);
// [abc1]
```
#### 함수 short-cut 만들기
특정 객체나 `prototype`에 선언된 함수를 변수에 저장해두고 사용하고 싶을 수 있습니다. 따라서 `해당함수.call`을 변수에 저장해 일반 함수로 호출하려고 할 것입니다.  
그러나 `call`의 경우 `this`값(호출해야 하는 함수)도 필요하기 때문에 호출할 수 없습니다. 이러한 경우 `bind()`를 통해 함수를 `call()`의 `this`값에 바인딩 할 수 있습니다.  
아래에서는 `Function.prototype.call`에 `Array.prototype.slice`를 바인딩해, 추가적인 call 호출을 줄였습니다.  
```javascript
let slice = Array.prototype.slice.call;
slice([1, 2, 3], 1, 2);
// Uncaught TypeError: slice is not a function

slice = Function.prototype.call.bind(Array.prototype.slice);
slice([1, 2, 3], 1, 2);
// [2]
```
### `apply()`와 `call()`의 차이점
`apply()`와 `call()`은 거의 동일하지만, `call`은 함수에 넘길 인수를 개별적인 list로 넘기지만, `apply`는 인수들을 하나의 객체(주로 `Array`)에 담아 전달합니다.  
#### 예시
- `call`: `func.call(this, "eat", "bananas")`
- `apply`:  `func.apply(this, ["eat", "bananas"])`
### `bind(thisArg, arg1, ..., argN)`
```javascript
const obj = {
  x: 40,
  getX: function (y, z) {
    return this.x + y + z;
  },
};

const unboundGetX = obj.getX;
console.log(unboundGetX()); // 이 함수는 전역 스코프 상에서 호출됩니다.
// 출력: NaN

const boundGetX = unboundGetX.bind(obj, 1); // getX 함수에 obj를 this로 바인딩하고, 첫 번째 인자(y)를 1로 대체합니다.
console.log(boundGetX(10));
// 출력: 51
```
`Function` 인스턴스에 주어진 `thisArg`로 `this` 키워드를 세팅하고, 주어진 `arg`들을 인수로 고정한 새로운 `function`을 반환합니다.  
#### 매개변수
 - `thisArg`: 바인딩된 함수가 호출될 때 지정될 `this` 값으로, 원시 값은 `Object` 로 변환되며 `null` 또는 `undefined`는 `strict mode`에서 전역 객체로 대체됩니다. `new` 연산자를 사용해 바인딩된 함수를 생성할 경우 이 값은 무시됩니다.  
 - `arg1, ..., argN`: 바인딩 된 함수에서 쓸 인수들입니다. 생략할 수 있습니다.  
#### 설명
`bind()` 함수는 새로운 바인딩 된 함수를 만들어내게 됩니다.  
바인딩 된 함수를 호출하게 되면, 래핑된 `타겟 함수(target function)`가 호출됩니다.  
`bind()`의 인자로 전달된 `thisArg`와 `arg`들을 내부 상태로 저장합니다.  
간단하게 설명하면, `const boundFn = fn.bind(thisArg, arg1, arg2)`는 `const boundFn = (...restArgs) => fn.call(thisArg, arg1, arg2, ...restArgs)`와 호출시 동일한 효과를 갖습니다. (`boundFn` 생성시에는 다릅니다.)  
##### 바인드 연결
```javascript
const boundFn = fn.bind(thisArg, arg1, arg2)
const boundFn2 = boundFn.bind({abc: "abc"}, arg3); // {abc: "abc"}는 무시됨
boundFn2(10); 
// boundFn2가 호출되면, boundFn가 호출되고, 이는 다시 fn을 호출합니다.
// fn이 최종적으로 받게되는 인수는 순서대로 "boundFn에 바인딩 된 인자", "boundFn2에 바인딩 된 인자", "boundFn2가 받은 인자"가 됩니다.
```
이렇게 바인딩 된 함수는, `bind()`를 통해 또 다른 바인딩 된 함수를 만들어낼 수 있습니다.   이때, 새롭게 바인딩 된 `thisArg` 값은 무시됩니다. 바인딩 대상 함수가 이미 바운드 된 `this`가 존재하기 때문입니다.  
##### `new` 키워드를 통한 생성
```javascript
class Base {
  constructor(...args) {
    console.log(new.target === Base);
    console.log(args);
  }
}

const BoundBase = Base.bind(null, 1, 2);

new BoundBase(3, 4); // true, [1, 2, 3, 4]
```
타겟 함수가 생성 가능한 경우, `new` 키워드를 통해 바인딩 된 함수를 만들어 낼 수 있습니다. 이렇게 하면 타겟 함수가 대신 생성된 것 처럼 동작합니다.  
다만, 주어진 `arg` 들은 타겟 함수에 제공되지만, `thisArg` 값은 무시됩니다.  
- `Reflect.construct`의 매개변수에 `thisArg`가 없는 것에서 볼 수 있듯, 함수 생성시 자체적으로 `this` 값을 준비하기 때문입니다.  

바인딩 된 함수가 직접 생성될 경우, `new.target`이 타겟 함수가 됩니다.  
또한, `instanceof`를 사용할 경우에도 타겟 함수로 취급됩니다.  
##### 상속 불가
```javascript
class Derived extends class {}.bind(null) {}
// TypeError: Class extends value does not have valid prototype property undefined
```
바인딩 된 함수에는 `prototype` 속성이 없으며, 상속의 대상이 될 수 없습니다.
### `Function`의 built-in property
`Function`의 빌트-인 속성으로 함수에 전달된 인수를 반환하는 `arguments`, 이 함수를 호출한 함수를 반환하는 `caller`가 있지만, 모두 표준이 아니며 `deprecated` 되었습니다.

# 참고 문서
- [Function.prototype.apply() - JavaScript | MDN (mozilla.org)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/apply)
- [Function.prototype.bind() - JavaScript | MDN (mozilla.org)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/bind)
- [Function.prototype.call() - JavaScript | MDN (mozilla.org)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/call)
- [Function() constructor - JavaScript | MDN (mozilla.org)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/Function)
