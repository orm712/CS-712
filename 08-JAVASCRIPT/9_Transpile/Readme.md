## `트랜스파일러(Transpiler)`?
`트랜스파일(Transpile)`이란, *한 프로그래밍 언어로 작성된 소스코드*를 입력으로 받아 **동일한, 또는 다른 프로그래밍 언어**로 된 `동등한 소스코드`를 생성하는 것을 말합니다.  
이러한 작업을 수행하는 코드 번역기를 `트랜스파일러(Transpiler)`라고 하며, `소스 간 컴파일러(Source-to-Source Compiler, S2S Compiler)`라고도 부릅니다.  
### 트랜스파일링의 목적
`트랜스파일링`은 주로 다음과 같은 목적을 위해 수행됩니다.  
#### 1. 소스 코드의 언어 변경
`파이썬`에서 `자바스크립트`로, `C`에서 `Rust`로 동일한 소스코드를 언어간 변환하는데 `트랜스파일러`를 사용하는 것으로, 가장 근본적인 목적이라고 할 수 있습니다.  
다양한 언어 간 변환을 지원하는 트랜스파일러들이 존재하며, [jarble/list-of-transpilers: A list of source-to-source compilers for various languages (github.com)](https://github.com/jarble/list-of-transpilers) 과 같이 타겟 언어 별로 정리된 자료들을 통해 이를 확인할 수 있습니다.  
#### 2. 동일 언어의 버전 간 호환
동일한 언어 내에서 버전간의 변화가 많은 경우(*ex. Python2<->Python3, 자바스크립트 ES5 <-> ES6*) 레거시 코드를 최신 버전에 맞게 수정하거나 이전 버전과의 호환성을 위해 수정하기 위해 사용하는 경우도 있습니다.  
Python2 문법으로 작성된 코드를 Python3 문법으로 변환해주는 [2to3](https://docs.python.org/ko/3/library/2to3.html) 라이브러리나, ES6 문법으로 작성된 코드를 현재 및 이전 브라우저/환경에 맞게 변환해주는 [Babel](https://babeljs.io/) 라이브러리가 대표적입니다.  
#### 3. 일괄적인 문법 변환
한 언어에 대해 특정한 방식의 문법으로 작성한 코드를 해당 언어의 기본 문법으로 변환해주는 트랜스파일러도 있습니다.  
대표적으로 자바스크립트에 추가로 정적 타입 문법을 제공하는 [TypeScript](https://www.typescriptlang.org/)로 작성된 코드를 자바스크립트 코드로 변환하는데 사용되거나, JSX 문법으로 작성된 코드를 자바스크립트 문법에 맞게 변환해주는 [facebookarchive/jstransform](https://github.com/facebookarchive/jstransform)(현재는 `Deprecated`되었고, `Babel`로 대체됨) 등이 있습니다.  
이외에도 자바스크립트 트랜스파일러가 포함된 [CoffeeScript](https://coffeescript.org/), [gdg/nodescript](https://github.com/gdg/nodescript)가 그러한 예 중 하나라고 할 수 있습니다.  
##### 예시 - CoffeeScript
```js
// CoffeeScript
opposite = true
number = -42 if opposite

// Javascript
var opposite, number;
opposite = true;
if(opposite) {
	number = -42;
}
```

### 컴파일러와의 차이
한 프로그래밍 언어로 작성된 `코드를 다른 언어로 번역`하는 **동작 방식**때문에 컴파일러와 종종 비교되기도 합니다. 보통 `컴파일러`가 트랜스파일러보다 **더 넓은 범주**를 의미하기 때문에, `트랜스파일러`가 `소스간 컴파일러`라고 불리우게 됩니다.  
둘의 차이를 만들어내는 가장 큰 특징은 `입력 코드의 언어와 대상 언어의 수준 차이`라고 할 수 있습니다.  
`트랜스파일러`는 *거의 동일한 수준*의 `추상화(Abstraction) 수준`에서 동작하는 프로그래밍 언어 간의 변환을 수행하는 반면, `컴파일러`의 경우 `고급 언어`에서 `저급 언어(어셈블리어, 기계어 등)`로의 변환을 주로 수행한다는 차이가 있습니다.  
## 자바스크립트의 트랜스파일러
상기했듯, 자바스크립트에는 여러 `트랜스파일러`들이 존재합니다.
[CoffeeScript](https://coffeescript.org/)처럼 문법을 단순화시키기위한 목적도 있지만, 주로 브라우저 간 호환성과 이전 버전 문법을 사용하는 환경과의 하위 호환성을 위해 사용됩니다.  
특히 브라우저 시장은 `Chakra(IE)`, `V8(Chromium-based Browser like Chrome, MS Edge, etc.)`,  `SpiderMonkey(Firefox)` 등 브라우저/기업 별로 사용하는 JS 엔진과 (브라우저 버전별로) 지원하는 ES 문법이 상이하기 때문에 트랜스파일러가 많이 사용됩니다.  
추가로, `트랜스파일러`들은 코드를 AST(추상 구문 트리) 형태로 파싱한 다음, 변경이 필요한 Node들을 찾아내 수정/삭제하거나 새로운 Node를 추가하는 등의 작업을 수행해 AST를 변화 시킵니다.  
이후 이를 바탕으로 코드를 생성해내는 방식으로 동작하게 됩니다.  
### [Babel](https://babeljs.io/)
그 중 가장 많이 사용되는 `트랜스파일러`로는 `Babel`이 있습니다.
`Babel`은 `ES6 문법으로 작성된 코드`를 *이전 버전의 자바스크립트와 호환되는 브라우저/환경에서 동작*할 수 있도록 **이전 버전으로 변환**해주는 `툴체인`입니다.  
이를 위해 `폴리필(Polyfill, 새로운 버전에 추가된 최신 기능을 이를 지원하지 않은 이전 버전의 환경에서 동작하도록 기존 코드의 동작방식을 수정하거나 새로운 함수를 추가하는 등의 내용을 포함하는 코드)`[#](https://developer.mozilla.org/en-US/docs/Glossary/Polyfill)기능을 지원하며(`core-js`와 같은 서드파티 폴리필을 통해), JSX, Typescript 등 다양한 문법으로 작성된 코드들의 자바스크립트로의 변환 등의 기능을 제공합니다.  
`Babel`에는 여러 프리셋들이 존재하며, 타겟 자바스크립트 버전에 맞게 코드를 변환해주는 [@babel/preset-env](https://babeljs.io/docs/babel-preset-env), React의 JSX 문법이나 DisplayName 생략 기능 등을 지원하는 [@babel/preset-react](https://babeljs.io/docs/babel-preset-react) 등이 존재합니다.  
또한 커스텀 플러그인을 만들어, [토스뱅크의 강현구님의 사례](https://toss.tech/article/27750)에서 특정 조건을 만족하는 AST의 Node에 대해 attribute를 추가하게 하는 것처럼 "`특정 조건을 만족하는 요소에 대한 일괄적인 코드 변경`"과 같은 용도로도 `트랜스파일러`를 사용할 수 있습니다.  
### [SWC](https://swc.rs/)
또 다른 `트랜스파일러`로는 `Vercel`의 [강동윤](https://github.com/kdy1)님께서 만드신 `SWC`가 있습니다.  
`SWC`는 `Rust`를 기반으로 하기때문에 `Javascript`로 작성된 `Babel`과 같은 번들러 대비 더 빠른 속도와 성능을 보여준다는 특징을 갖고 있습니다.  
- [SWC 문서의 벤치마크 자료](https://swc.rs/docs/benchmarks)

Babel과 마찬가지로 `Javascript 버전간의 트랜스파일링`(폴리필과 함께), `타입스크립트 컴파일` 등을 지원하며 ES 모듈을 CommonJS, AMD 등의 모듈로 트랜스파일 할 수도 있습니다.  

# 참고
- [Babel · Babel (babeljs.io)](https://babeljs.io/)
- [Transpiler, “사용”말고 “활용”하기 (toss.tech)](https://toss.tech/article/27750)
- [Top 3 JavaScript Transpilers/Compilers (byby.dev)](https://byby.dev/js-transpilers)
- [Polyfills and transpilers (javascript.info)](https://javascript.info/polyfills)
- [Understanding Transpiling in JavaScript (webreference.com)](https://webreference.com/javascript/advanced/transpilers/)
- [Source-to-source compiler - Wikipedia](https://en.wikipedia.org/wiki/Source-to-source_compiler)
- [Bundling & Transpiler | 위펄슨 기술 블로그 (weperson.com)](https://tech.weperson.com/wedev/frontend/bundling-transpiler/)
- [jarble/list-of-transpilers: A list of source-to-source compilers for various languages (github.com)](https://github.com/jarble/list-of-transpilers)
- [List of languages that compile to JS · jashkenas/coffeescript Wiki (github.com)](https://github.com/jashkenas/coffeescript/wiki/List-of-languages-that-compile-to-JS)
- [gdg/nodescript: NodeScript - JavaScript without the Variable Declarations and Semicolons (github.com)](https://github.com/gdg/nodescript)
- [Transpiler:Bridging Programming Languages in the Digital Era (codeparrot.ai)](https://10xdev.codeparrot.ai/transpiler)
- [Rust-based platform for the Web – SWC](https://swc.rs/)