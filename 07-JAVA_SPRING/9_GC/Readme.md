# Garbage Collection
[GC](..%2F..%2F05-ETC%2F10_Garbage_Collection%2FReadme.md)

---

## finalize() 를 수동으로 호출하는 것은 왜 문제가 될 수 있을까요?
> - `finalize()`는 객체가 GC되기 전에 수행해야 할 정리 작업을 정의하는 메서드
>   - Java의 `Object`클래스에 기본적으로 제공되며, 오버라이드할 수도 있음
> - Java 9부터 `finalize()`메서드는 `사용되지 않는 것이 권장`
## 권장되지 않는 이유
### 1. 가비지 컬렉션의 의미 상실
> - `finalize()`는 가비지 컬렉션 도중에 호출되도록 설계
> - 수동으로 호출하면, 객체가 가비지 컬렉션되지 않았더라도 `finalize()` 메서드가 호출될 수 있다.
>   - 가비지 컬렉션 메커니즘의 의미 상실

### 2. 이중 해제 문제
> - `finalize()`는 리소스를 해제하는 데 사용되는 메소드
> - 수동으로 호출하면, 이후 가비지 컬렉션에 의해 다시 호출되어 같은 리소스를 두 번 해제할 수 있다.
>   - 오류 발생

### 3. 대체 메커니즘
> - Java 9부터는 `java.lang.ref.Cleaner` 또는 `java.lang.ref.PhantomReference`가 나왔다.
>   - finalize()보다 덜 위험하긴 하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.

## 어떤 변수의 값이 null이 되었다면, 이 값은 GC가 될 가능성이 있을까요?
> - 값이 null인 변수는 해당 변수가 참조하던 객체를 더 이상 참조하지 않기 때문에, GC의 대상이 될 수 있다. 
> - 하지만, 값이 null인 변수가 GC의 대상이 될 수 있는지 여부는 해당 변수를 참조하는 다른 변수나 객체의 `참조 상황에 따라 달라질 수 있다.`
> - GC가 수행되는 시점에서 해당 변수가 `더 이상 참조되지 않는다면`, GC의 대상이 될 수 있다.

```java
MyObject obj = new MyObject();
obj = null; // MyObject 인스턴스는 더 이상 참조되지 않음
```

```java
List<MyObject> list = new ArrayList<>();
MyObject obj = new MyObject();
list.add(obj);
obj = null; // MyObject 인스턴스는 여전히 list에 의해 참조됨 -> GC 대상 X

```

### 요약
> 웬만하면 Cleaner, finalize를 사용하지 말자.