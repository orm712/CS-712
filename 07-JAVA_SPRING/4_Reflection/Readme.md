# Reflection이란? [#Oracle 공식 문서](https://www.oracle.com/technical-resources/articles/java/javareflection.html)
`Reflection`은 Java의 기능 중 하나로, 실행중인 Java 프로그램이 스스로를 검사하거나 내관(introspect)하고, 프로그램 내부 속성(`클래스`, `인터페이스`, `필드 값` 및 `메서드`)을 조작할 수 있게 해줍니다.  
이는 컴파일 시간에 이름을 모르는 속성을 사용해야 할 때 특히 유용합니다.  
또한, Reflection을 통해 새로운 객체를 인스턴스화 하거나, 메서드를 호출(invoke)하고 필드 값을 얻거나 설정할 수 있습니다.  
이러한 기능은 **`Java만의 특징`** 으로 Pascal, C, C++와 같은 언어로 작성된 프로그램은 프로그램 내에서 정의된 함수에 대한 정보를 얻을 수 있는 방법이 없습니다.  
## 클래스/메서드 정보 가져오기
먼저 Reflection을 사용하기 위해선 JDK내에 있는 `java.lang.reflect` 패키지를 import 해야 합니다.  
이후 클래스, 필드 값 및 메서드 정보를 얻고자하는 인스턴스에 대해 `getClass()`(`Object`) 메서드를 호출해 해당 객체의 런타임 클래스 표현을 얻어옵니다.  
- 반환된 `Class` 타입의 객체는 클래스 정보에 접근하기 위한 메서드들을 제공합니다.
### 필드 이름 가져오기
```java
	Field[] fields = Class.getDeclaredFields();
```
### 클래스 이름 가져오기
`Class` 클래스의 `getSimpleName()`, `getName()`, `getCanonicalName()` 메서드를 통해 인스턴스의 클래스 이름을 얻을 수 있습니다.  
또한, `Class.forName("정규화된 클래스 이름")`을 통해 해당 클래스의 인스턴스를 만들 수 있습니다. 이때, 일치하는 클래스 이름이 없으면 _`ClassNotFoundException`_ 이 발생합니다.
#### `Class.getSimpleName()`
객체의 선언에 나타나있는 기본 이름을 반환합니다.
#### `Class.getName()`, `Class.getCanonicalName()`
<i><u>패키지 선언을 포함한</u></i> 정규화된 클래스 이름을 반환합니다.
### 클래스 수식어(또는 지정자, 제한자) 가져오기
`Class` 클래스의 `getModifiers()` 메서드를 호출하면 해당 클래스의 수식어 정보를 저장하고 있는int 타입의 플래그 비트를 반환합니다.  
이 플래그 비트는 `java.lang.reflect.Modifier`에서 제공하는 `isPublic()`, `isAbstract()`정적 메서드들을 통해 특정 수식어가 존재하는지 확인하는데 사용할 수 있습니다.
### 패키지 정보 가져오기
`Class` 클래스의 `getPackage()` 메서드를 호출하면 해당 클래스가 속한 패키지에 대한 정보들로 번들링된 [`Package`](https://docs.oracle.com/javase/8/docs/api/java/lang/Package.html) 클래스 객체를 반환합니다.
### 부모 클래스(Superclass) 정보 가져오기
`Class` 클래스의 `getSuperclass()` 메서드를 호출하면 해당 클래스의 부모 클래스 `Class` 객체를 얻을 수 있습니다.
- 라이브러리 또는 Java에 빌트인 되어있는 클래스의 경우, 객체의 부모 클래스를 알기 힘든 경우가 많은데 이런 경우 유용합니다.
### 클래스가 구현한 인터페이스 정보 가져오기
`Class` 클래스의 `getInterfaces()` 메서드를 호출하면 해당 클래스가 구현한 인터페이스 `Class` 객체의 배열을 얻을 수 있습니다. (인터페이스 1개만 구현했다면 원소가 1개인 배열을 반환합니다.)  
이때, 해당 클래스가 `implements` 키워드를 통해 '구현할 것'이라고 **선언**한 인터페이스들만 반환 됩니다. 즉, 부모 클래스들이 구현한 인터페이스들은 반환되지 않습니다.
## 생성자/메서드/필드 값 다루기
아래에서 다루는 `getXXX`/`getDeclaredXXX`는 모두 `getXXX`로 통일해 작성했습니다.
- `getXXX`와 `getDeclaredXXX`의 차이: `getXXX`의 경우 해당 클래스는 물론 부모 클래스에서 선언된 것도 반환하지만, `getDeclaredXXX`의 경우 현재 클래스내에서 선언된 것만 반환합니다.
### 생성자 다루기
`Class` 클래스의 `getConstructors()` 메서드를 호출하면 해당 클래스의 *생성자 정보들로 번들링 된* `Constructor` 클래스 배열을 반환합니다.
#### 특정 생성자 가져오기
`getConstructor()`에 실제 선언되어있는 순서대로 인수들의 클래스(ex. int라면 `int.class`)를 파라미터로 넘겨주면 해당 시그니처를 갖는 생성자를 반환합니다.
- ex. `getConstructor(String.class, int.class)` 라고 호출하면, 첫 번째 인자로 String 값을 받고 두 번째 인자로 int 값을 받는 생성자를 반환합니다.
- 만약 일치하는 시그니처를 갖는 생성자가 존재하지 않는다면 `NoSuchMethodException`이 발생합니다.
이는 Java에서 생성자 간 메서드 시그니처가 겹칠 수 없다는 점을 활용한 것 입니다.
#### `Constructor` 클래스로 인스턴스 만들기
`Constructor` 클래스의 `newInstance(매개변수)` 메서드를 호출하면 됩니다. 이때, 디폴트로 Object 형태로 반환됩니다.
- `Class` 클래스의 `newInstance()` 메서드를 이용해 인스턴스를 만들 수도 있지만, 이는 Java 9부터 Deprecated 되었기 때문에 Constructor 클래스를 활용해 인스턴스를 만드는 것이 권장됩니다.

### 메서드 다루기
Reflection을 이용하면 *메서드는 물론* **오버로딩된 메서드**를 **런타임 시간**에 호출할 수 있습니다.
#### 메서드 가져오기
`Class` 클래스의 `getMethods()` 메서드를 호출하면 해당 클래스는 물론 상위 클래스에서 갖고있는 public 메서드들을 불러옵니다.
`getDeclaredMethods()`를 호출하면 해당 클래스에서 선언된 메서드들만 불러옵니다.
#### 단일 메서드 가져오기
`getMethod(함수 명, 인수 클래스...)`또는 `getDeclaredMethod()`를 통해 단일 메서드를 불러올 수 있습니다.
이때 인자로 함수 명을(*있다면 인수들의 타입 클래스까지*) 넘겨주면 됩니다.
- 즉, int 타입의 인자를 받는 make라는 메서드가 있다면 `getDeclaredMethod("make", int.class)`와 같은 형태인 것입니다.
#### 메서드 호출하기
`Method` 클래스의 `invoke(instance, 인자들)` 메서드를 호출하면 해당 메서드를 실행할 수 있습니다.
### 필드 값 다루기
#### 필드 가져오기
Class 클래스의 `getFields()`또는 `getField(fieldName)` 메서드를 호출하면 해당 클래스 및 모든 부모 클래스의 접근 가능한 public 필드의 *필드 값에 대한 정보를 갖는* `Field` 클래스(들)을 반환합니다.
만약 private으로 선언된 필드 값에 접근하고 싶다면 `getDeclaredField(fieldName)` 또는 `getDeclaredFields()`을 이용하면 접근할 수 있습니다.
- 만약 올바른 필드 이름을 넘기지 못했다면, `NoSuchFieldException`을 던지게 됩니다.
#### 필드 타입 가져오기
`Field` 클래스의 `getType()` 메서드를 호출하면 해당 필드의 타입 `Class` 객체를 반환합니다.
#### 필드 값 가져오기
`Field` 클래스의 `get(instance)` 또는 `getXXX(instance)` 메서드를 호출하면 주어진 인스턴스에서 해당 필드가 무슨 값을 갖고있는지 반환합니다.  
- `get()` 메서드를 호출하면 `Object` 타입으로, `getXXX()` (XXX = Int, Boolean 등 **원시타입**) 메서드를 호출하면 해당 타입으로 반환합니다.  
만약 `public static`으로 선언된 필드라면, 별도로 instance를 넘기지 않아도 값을 가져오거나 수정할 수 있습니다.  
필드의 디폴트 값을 가져오고 싶다면 instance로 `null`을 넘겨주면 됩니다.
#### 필드 값 바꾸기
`Field` 클래스의 `set(instance, 값)` 메서드를 호출하면 필드 값을 바꿀 수 있습니다.
`Field` 클래스의 `setAccessible(boolean)` 메서드를 호출하면 필드 **접근 가능 여부**를 조작할 수 있습니다.
- 이때, 접근 가능 여부란, *실제 필드의 접근 제어자*를 *바꾸는 것이 아니라*, `AccessibleObject`(여기선 `Field`)의 동작을 바꾸는 것입니다.
## Reflection의 장점
## Reflection의 단점
# Reflection은 보안적인 문제가 있는가?
Reflection의 메서드들을 이용하면 private으로 선언된 값에 접근할 수 있어 객체지향 프로그래밍의 중요 원칙 중 하나인 캡슐화를 위반합니다.  
따라서 특정 환경에서 악성코드가 Reflection을 이용해 중요한 리소스에 무단 접근하거나, 보안 정책을 위반할 수 있습니다.
## 방지책
Java 9 부터 도입된 `Module`을 사용하면 더 이상 내부를 노출하지 않을 수 있습니다. 또한, Reflection 액세스 권한을 부여할 수 있는 방법도 존재해 필요한 경우 내부를 들여다 볼 수 있게 할 수 있습니다.
# Reflection은 어떻게 활용 가능할까?
JavaBeans가 Reflection을 실제로 활용한 대표적인 예시입니다.  
JavaBeans는 소프트웨어 컴포넌트들을 빌더 툴을 통해 시각적으로 조작할 수 있습니다. 이때, 이 빌더 툴이 Reflection을 이용해 Java 컴포넌트(class들)들이 동적으로 로드 될 때, 이들의 속성을 가져옵니다.