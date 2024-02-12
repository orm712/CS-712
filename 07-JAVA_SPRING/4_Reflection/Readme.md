# Reflection이란? [#Oracle 공식 문서](https://www.oracle.com/technical-resources/articles/java/javareflection.html)
`Reflection`은 Java의 기능 중 하나로, 실행중인 Java 프로그램이 스스로를 검사하거나, 프로그램 내부 속성(`클래스`, `인터페이스`, `필드 값` 및 `메서드`)을 조작할 수 있게 해줍니다.  
이는 *컴파일 시간*에 **이름을 모르는 속성**을 **사용**해야 할 때 **특히 유용**합니다.  
또한, Reflection을 통해 **새로운 객체를 인스턴스화** 하거나, **메서드를 호출(invoke)** 하고 **필드 값을 얻거나 설정**할 수 있습니다.  
이러한 기능은 **`Java만의 특징`** 으로 Reflection이 처음 나온 *1997년 당시*에는 Pascal, C, C++와 같은 언어로 작성된 프로그램은 프로그램 내에서 정의된 함수에 대한 정보를 얻을 수 있는 방법이 없습니다.
- 다만 현대에는 C#, Javascript, Python, PHP와 같은 언어에서 Reflection을 지원하고 있습니다.
## 클래스/메서드 정보 가져오기
먼저 Reflection을 사용하기 위해선 JDK내에 있는 `java.lang.reflect` 패키지를 import 해야 합니다.  
이후 클래스, 필드 값 및 메서드 정보를 얻고자하는 인스턴스에 대해 `getClass()`(`Object`) 메서드를 호출해 해당 객체의 런타임 클래스 표현을 얻어옵니다.
- 반환된 `Class` 타입의 객체는 클래스 정보에 접근하기 위한 메서드들을 제공합니다.
### 클래스 이름 가져오기
`Class` 클래스의 `getSimpleName()`, `getName()`, `getCanonicalName()` 메서드를 통해 인스턴스의 클래스 이름을 얻을 수 있습니다.  
또한, `Class.forName("정규화된 클래스 이름")`을 통해 해당 클래스의 인스턴스를 만들 수 있습니다. 이때, 일치하는 클래스 이름이 없으면 _`ClassNotFoundException`_ 이 발생합니다.
```java
	Object abc = new ABC();
	Class<?> classInfo = abc.getClass();
	// 1. Class.getSimpleName() : 객체의 선언에 나타있는 기본 이름을 반환한다.
	// "ABC"
	System.out.println(classInfo.getSimpleName());
	// 2. Class.getName(), `Class.getCanonicalName()
	// "패키지 선언을 포함한" 정규화된 클래스 이름을 반환합니다.
	// "com.example.reflection.ABC"
	System.out.println(classInfo.getName());
	System.out.println(classInfo.getCanonicalName());
```
이외에도 필드 값의 이름을 가져오고프면 `Field` 클래스의 `getName()`을 호출하고, 메서드의 이름을 가져오고프면 `Method`  클래스의 `getName()`을 호출하면 된다.
### 클래스/필드/메서드 수식어(또는 지정자, 제한자) 가져오기
`Class`/`Field`/`Method` 클래스의 `getModifiers()` 메서드를 호출하면 해당 속성의 수식어 정보를 저장하고 있는 int 타입의 **플래그 비트**를 반환합니다.  
이 플래그 비트는 `java.lang.reflect.Modifier`에서 제공하는 `isPublic()`, `isAbstract()`정적 메서드들을 통해 특정 수식어가 존재하는지 확인하는데 사용할 수 있습니다.
```java
	Object abc = new ABC();
	Class<?> classInfo = abc.getClass();
	int abcMods = classInfo.getModifiers();
	// ABC 클래스가 public으로 선언되어 있는지 출력합니다.
	// "true"
	System.out.println(Modifier.isPublic(abcMods));
```
### 패키지 정보 가져오기
`Class` 클래스의 `getPackage()` 메서드를 호출하면 해당 클래스가 속한 패키지에 대한 정보들로 번들링된 [`Package`](https://docs.oracle.com/javase/8/docs/api/java/lang/Package.html) 클래스 객체를 반환합니다.
```java
	Object abc = new ABC();
	Class<?> classInfo = abc.getClass();
	Package pkgInfo = classInfo.getPackage();
	// ABC 클래스가 속한 패키지 이름을 출력합니다.
	// "com.example.reflection"
	System.out.println(pkgInfo.getName());
```
### 부모 클래스(Superclass) 정보 가져오기
`Class` 클래스의 `getSuperclass()` 메서드를 호출하면 해당 클래스의 부모 클래스 `Class` 객체를 얻을 수 있습니다.
- **라이브러리** 또는 **Java에 빌트인 되어있는 클래스**의 경우, 객체의 부모 클래스를 알기 힘든 경우가 많은데 이런 경우 유용합니다.
```java
	Object abc = new ABC();
	Class<?> classInfo = abc.getClass();
	Class<?> superClassInfo = classInfo.getSuperclass();
	// ABC 클래스의 부모인 Object 클래스를 출력합니다.
	// "Object"
	System.out.println(superClassInfo.getSimpleName());
```
### 클래스가 구현한 인터페이스 정보 가져오기
`Class` 클래스의 `getInterfaces()` 메서드를 호출하면 해당 클래스가 구현한 인터페이스 `Class` 객체의 배열을 얻을 수 있습니다.
- 이때, 해당 클래스가 `implements` 키워드를 통해 '**구현할 것**이라고 **선언**'한 인터페이스들만 반환 됩니다. 즉, *부모 클래스들이 구현한 인터페이스들*은 **반환되지 않습니다**.
```java
	Object abc = new ABC();
	Class<?> classInfo = abc.getClass();
	// ABC 클래스의 경우 구현한 인터페이스가 없으므로
	// 아래 배열은 빈 배열 입니다.
	Class<?> abcInterfaceInfo[] = classInfo.getInterfaces();
```
## 생성자/메서드/필드 값 다루기
> 아래에서 `getXXX`/`getDeclaredXXX`이 혼용되어 나옵니다.  
> `getXXX`와 `getDeclaredXXX`의 차이: `getXXX`의 경우 *해당 클래스는 물론*  **상속한 클래스**, **구현한 인터페이스**에서 선언된 것도 반환하지만, `getDeclaredXXX`의 경우 현재 **클래스내에서** 선언된 것만 반환합니다.  
> 또한, `getXXX`의 경우 **public으로 선언된 것**만 반환하지만, `getDeclaredXXX`의 경우 클래스내에서 선언된 **모든 요소들**을 가져옵니다.
### 생성자 다루기
`Class` 클래스의 `getConstructors()` 메서드를 호출하면 해당 클래스의 *생성자 정보들로 번들링 된* `Constructor` 클래스 배열을 반환합니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	Constructor<?>[] constructors = abcClass.getConstructors();
```
#### 특정 생성자 가져오기
`getConstructor()`에 *실제 선언되어있는 순서대로* 인수들의 클래스(ex. int라면 `int.class`)를 파라미터로 넘겨주면 해당 시그니처를 갖는 생성자를 반환합니다.
- 만약 일치하는 시그니처를 갖는 생성자가 존재하지 않는다면 `NoSuchMethodException`이 발생합니다.
이는 Java에서 **생성자 간 메서드 시그니처가 겹칠 수 없다**는 점을 활용한 것 입니다.
```java
class ABC {
	ABC(String Str, int Int) {
		...
	}
}
...
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	// 첫 번째 인자로 String 값을 받고 두 번째 인자로 int 값을 받는 생성자를 반환합니다.
	Constructor<?> constructor = abcClass.getConstructor(String.class, int.class);
```
#### `Constructor` 클래스로 인스턴스 만들기
`Constructor` 클래스의 `newInstance(매개변수)` 메서드를 호출하면 됩니다. 이때, 디폴트로 `Object` 형태로 반환됩니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	Constructor<?> constructor = abcClass.getConstructor(String.class, int.class);
	// 기본 반환타입이 Object이므로, ABC 클래스 변수에 할당하려면 명시적 형변환이 필요합니다.
	ABC abc = (ABC) constructor.newInstance("HI", 123);
```
- `Class` 클래스의 `newInstance()` 메서드를 이용해 인스턴스를 만들 수도 있지만, 이는 **Java 9**부터 **`Deprecated`** 되었기 때문에 `Constructor` **클래스를 활용**해 인스턴스를 만드는 것이 **권장**됩니다.

### 메서드 다루기
Reflection을 이용하면 *메서드는 물론* **오버로딩된 메서드**를 **런타임 시간**에 호출할 수 있습니다.
#### 메서드 가져오기
`Class` 클래스의 `getMethods()`  또는 `getDeclaredMethods()`를 호출하면 해당 클래스의 메서드들을 불러옵니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	// 해당 클래스에서는 물론 상속한 클래스, 구현한 인터페이스에서 갖고있는 public 메서드들을 가져옵니다.
	Method methods1[] = abcClass.getMethods();
	// 해당 클래스 내에서 선언된 모든 메서드들을 가져옵니다.
	Method methods2[] = abcClass.getDeclaredMethods();
```
#### 단일 메서드 가져오기
`getMethod(함수 명, 인수 클래스...)`또는 `getDeclaredMethod()`를 통해 단일 메서드를 불러올 수 있습니다.
이때 인자로 **함수 명**을(*있다면 인수들의 타입 클래스까지*) 넘겨주면 됩니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	// 해당 클래스 내에서 선언된 메서드 중 첫 번째 인자로 int를 받는 "make" 라는 메서드를 가져옵니다.
	Method method = abcClass.getDeclaredMethod("make", int.class);
```
#### 메서드 호출하기
`Method` 클래스의 `invoke(instance, 인자들)` 메서드를 호출하면 해당 메서드를 실행할 수 있습니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	ABC abc = (ABC) abcClass.getConstructor().newInstance();
	// 해당 클래스 내에서 선언된 메서드 중 첫 번째 인자로 int를 받는 "make" 라는 메서드를 가져옵니다.
	Method method = abcClass.getDeclaredMethod("make", int.class);
	// abc 변수에 저장된 인스턴스로 make 라는 함수를 인자로 10을 넘겨 실행하는 코드입니다.
	method.invoke(abc, 10);
```
### 필드 값 다루기
Class 클래스의 `getFields()`또는 `getDeclaredFields()` 메서드를 호출하면 해당 클래스의 필드의 *필드 값에 대한 정보를 갖는* `Field` 클래스들을 반환합니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	// ABC 클래스는 물론 부모 클래스, 구현한 인터페이스가 갖고있는 public 필드들을 가져옵니다.
	Field fields1[] = abcClass.getFields();
	// ABC 클래스내에서 선언된 모든 필드들을 가져옵니다.
	Field fields2[] = abcClass.getDeclaredFields();
```
#### 단일 필드 가져오기
만약 하나의 필드 값을 가져오고 싶다면 `getDeclaredField(fieldName)` 또는 `getField(fieldName)`을 이용하면 가져올 수 있습니다.
- 만약 올바른 필드 이름을 넘기지 못했다면, `NoSuchFieldException`을 던지게 됩니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	// ABC 클래스는 물론 부모 클래스, 구현한 인터페이스가 갖고있는 public 필드들 중 "Number" 라는 이름의 필드를 가져옵니다.
	Field field1 = abcClass.getField("Number");
	// ABC 클래스내에서 선언된 필드들 중 "Age" 라는 이름의 필드를 가져옵니다.
	Field field2 = abcClass.getDeclaredField("Age");
```
#### 필드 타입 가져오기
`Field` 클래스의 `getType()` 메서드를 호출하면 해당 필드의 타입 `Class` 객체를 반환합니다.
```java
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	Field field = abcClass.getField("Number");
	// "Number" 필드의 타입에 대한 정보를 갖는 Class를 반환합니다.
	Class<?> fieldClass = field.getType();
```
#### 필드 값 가져오기
`Field` 클래스의 `get(instance)` 또는 `getXXX(instance)` 메서드를 호출하면 주어진 인스턴스에서 해당 필드가 어떤 값을 갖고있는지 반환합니다.  
```java
	// ABC 클래스는 int 타입의 Number 필드를 갖고 있습니다.
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	ABC abc = (ABC) abcClass.getConstructor().newInstance();
	Field field = abcClass.getField("Number");
	// abc 변수에 저장된 인스턴스가 갖고있는 Number 필드 값을 반환합니다.

	// get() 메서드를 호출하면 Object 타입으로,
	// 여기서는 Number가 int 타입이므로, 래퍼 클래스인 Integer 타입으로 반환됩니다.
	Object fieldValue = field.get(abc);
	
	// getXXX() (XXX = Int, Boolean 등 원시 타입) 메서드를 호출하면 해당 타입으로 반환합니다.
	int fieldIntValue = field.getInt(abc);

	// 해당 필드의 디폴트 값을 가져오고 싶다면 instance로 null을 넘겨주면 됩니다.
	// 만약 정적 필드라면, 별도로 instance를 넘기지 않아도 값을 불러올 수 있습니다.
	// (instance를 넘겨도 해당 instance는 무시됩니다.)
	int defaultValue = field.get(null);	
```

#### 필드 값 바꾸기
`Field` 클래스의 `set(instance, 값)` 메서드를 호출하면 필드 값을 바꿀 수 있습니다.
`Field` 클래스의 `setAccessible(boolean)` 메서드를 호출하면 필드 **접근 가능 여부**를 조작할 수 있습니다.
- 이때, 접근 가능 여부란, *실제 필드의 접근 제어자*를 *바꾸는 것이 아니라*, `AccessibleObject`(여기선 `Field`)의 동작을 바꾸는 것입니다.
	>  해당 값이 true인 경우 **reflection의 대상이 된 객체**(여기서는 `Field`)는 사용 될 때 `자바 언어 접근 체킹(Java Language Access checking)`을 억제해야 함을 의미합니다.  
	> 즉, 해당 객체를 사용할 때 이것이 *public 인지 여부*와 같은 것을 검사하는 **액세스 검사**가 **이뤄지지 않음**을 의미합니다.
```java
	// ABC 클래스는 private으로 설정된 int 타입의 Number 필드를 갖고 있습니다.
	Class<?> abcClass = Class.forName("com.example.reflection.ABC");
	ABC abc = (ABC) abcClass.getConstructor().newInstance();

	// Number 필드를 가져왔지만 값에 접근할 순 없습니다.
	Field field = abcClass.getDeclaredField("Number");
	// java.lang.IllegalAccessException
	// Class "main이 존재하는 클래스 명" can not access a member of class ABC with modifiers "private"
	System.out.println(field.get(abc));
	// 해당 필드의 Accessible을 true로 바꾸었기 때문에 이제 접근할 수 있습니다.
	field.setAccessible(true);
	// "10"
	System.out.println(field.get(abc));
```
## Reflection의 장점
### 동적 구성
Reflection이 동적 프로그래밍을 할 수 있게 해주어, 애플리케이션의 유연성과 융통성을 향사시킵니다.  
이러한 장점은 필요한 클래스나 모듈이 런타임 시간 전에는 알 수 없는 경우 특히 유용합니다.  
Reflection의 유연함을 활용해, 개발자는 코드 변경의 번거로움 없이 실시간으로 구성을 바꿀 수 있는 시스템을 만들 수 있습니다.
#### 예시 - Spring
일례로, Spring은 Reflection을 이용해 Bean들을 생성하고 구성합니다.  
이러한 과정은 classpath 내 컴포넌트들을 감지하고 동적으로 인스턴스화 한 뒤, 애너테이션과 XML 설정을 바탕으로 구성하므로써 이뤄집니다.  
이를 통해 개발자들은 소스 코드의 수정 없이 Bean을 추가하거나 수정할 수 있습니다.  
### 확장성
Reflection을 이용해 개발자는 애플리케이션의 핵심 코드의 변경 없이 새로운 기능(Functionality)이나 모듈을 런타임에 포함시킬 수 있습니다.  
예시로, 우리가 베이스 클래스를 정의하고 `다형성 역직렬화(Polymorphic Deserialization)`를 위해 여러 서브타입들을 통합하는 서드 파티 라이브러리를 쓴다고 가정하겠습니다.  
이때, 우리는 베이스 클래스를 확장하는 자체 커스텀 서브타입을 도입해 기능을 확장하고자 합니다.  
이러한 경우 Reflection을 사용하면 커스텀 서브 타입을 런타임에 동적으로 등록할 수 있고, 서드 파티 라이브러리와 쉽게 통합 할 수 있게 됩니다.  
따라서, 우리는 코드를 변경치 않고, 라이브러리를 특정 요구사항에 맞게 조정할 수 있습니다.  
- `다형성 역직렬화(Polymorphic Deserialization)`: JSON 직렬화/역직렬화 라이브러리인 `Jackson`의 특징 중 하나로, 하나의 부모와 여러 하위 클래스가 있을 때 역직렬화 시 객체의 올바른 실제 유형을 결정하는 것입니다.  
### 코드 분석
소프트웨어 퀄리티를 높이기 위해 Reflection을 통해 코드를 동적으로 분석할 수 도 있습니다.  
일례로 [`ArchUnit`](https://www.archunit.org/) 이라는 자바 구조 단위 테스트 라이브러리의 경우, [Reflection과 Bytecode 분석을 이용](https://www.archunit.org/motivation)합니다.  
만약 Reflection 단에서 가져올 수 없는 정보가 있다면, Bytecode 수준의 분석을 통해 정보를 가져옵니다.

## Reflection의 단점
### 성능 오버헤드
Reflection은 동적으로 타입을 결정하기 때문에 이는 JVM의 최적화를 제한할 수 있습니다.  
따라서 성능에 민감한 상황이라면 자주 호출되는 코드에서 Reflection 사용을 자제하는 것을 고려해야 합니다.
### 내부 사항의 노출
Reflection을 활용하면 그렇지 않은 코드에서는 제한될 수 있는 동작을 수행할 수 있습니다.  
앞서 사용 용례에서도 보였듯이, private 필드와 메서드에 접근하고 조작할 수 있던 것이 그 예입니다.
이러한 행위는 객체지향 프로그래밍의 중요 원칙 중 하나인 캡슐화를 위반하는 행위입니다.  
### 컴파일 시간 안전성의 손실
일반적인 Java 개발에선 컴파일러의 도움으로 타입 검사는 물론 클래스, 메서드, 필드가 올바르게 사용되는지 확인할 수 있습니다.  
Reflection을 사용하면 이러한 검사가 이뤄지지 않으므로 탐지하기 힘든, 런타임 상에서 발생하는 버그를 유발할 수 있습니다.
### 코드의 유지보수성 저하
애플리케이션의 코드가 Reflection에 크게 의존할 경우, 코드 가독성이 떨어지게 되고 이는 유지보수성의 저하를 유도합니다.  
또한 모든 개발 툴이나 IDE가 완전히 Reflection을 지원하지 않기때문에 몇몇 부분은 개발자가 수동으로 검사해야할 수 있습니다.
### 보안 문제
특정 환경에서 악성코드가 Reflection을 활용해 민감한 자원에 허가되지 않은 접근을 하거나, 보안 규정을 위반하는 동작을 수행할 수 있기 때문에 Reflection 접근을 허용토록 하는것은 위험합니다.

# Reflection은 보안적인 문제가 있는가?
Reflection의 메서드들을 이용하면 **private으로 선언된 값에 접근**할 수 있어 객체지향 프로그래밍의 중요 원칙 중 하나인 **캡슐화**를 **위반**합니다.  
따라서 특정 환경에서 악성코드가 Reflection을 이용해 중요한 리소스에 무단 접근하거나, 보안 정책을 위반할 수 있습니다.
## 방지책 - `Module`
Java 9 부터 도입된 `Module`을 사용하면 더 이상 내부를 노출하지 않을 수 있습니다. 또한, Reflection 액세스 권한을 부여할 수 있는 방법(`open` 키워드 사용)도 존재해 필요한 경우 내부를 들여다 볼 수 있게 할 수 있습니다.
# Reflection은 어떻게 활용 가능할까?
앞서 나온 장점들의 예시처럼, Reflection은 **동적인 객체 생성**, **코드 분석** 등 다양한 작업에 유용하게 사용할 수 있습니다.  
`Spring`, `Hibernate`와 같은 유명 라이브러리에서 의존성 주입, Bean 조작 등을 수행할 때, Reflection을 활용합니다.

# 참고 문서
- https://www.baeldung.com/java-reflection
- https://www.baeldung.com/java-reflection-benefits-drawbacks
- https://www.oracle.com/technical-resources/articles/java/javareflection.html