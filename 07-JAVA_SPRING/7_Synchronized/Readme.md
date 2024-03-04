## `synchronized` 키워드란?
***멀티 스레드 환경***에서 Java 언어가 **공유 자원**에 대한 **동기화 처리**를 위해 사용되는 관용구(idiom)입니다. 이 키워드를 사용해 개발자는 간단하게 **`경쟁 조건(Race Condition)`** 을 **피할 수** 있습니다.  
`Synchronized` 키워드를 사용하게 되면 Java는 내부적으로 **`모니터(monitor)`**(`모니터 락(monitor lock)` 또는 `내재적 락(intrinsic lock)`이라고도 불림)을 사용해 **동기화를 처리**합니다.  
모니터는 동기화에 필요한 '객체 상태에 대한 **배타적 액세스 강제**', '**가시성**(**visibility**)에 필수적인 `발생-전(happen-before) 관계` 설정' 과 같은 작업을 수행하는 역할을 합니다.  
- 가시성: 동일한 `synchronized` 블록에 진입하는 다음 스레드는 *이전 스레드가 본 것*과 **같은 변수 값**들을 보게 됩니다.
- `발생-전(happen-before)`: **명령어의 순서 변경**을 통해 작업 간의 순서 및 가시성을 보장하는 것을 말합니다. 이를 통해 *한 스레드에서 변경된 내용*이 **다른 스레드에 표시될 수 있도록 보장**합니다.

모든 객체는 관련된 `내재적 락`이 존재하고, 관례에 따라 객체의 필드에 **독점적이고 일관적**이게 **접근**해야하는 스레드는 '내재적 락'을 얻고 접근 후 '내재적 락'을 해제해야 합니다.  
이러한 모니터는 **객체에 바인딩** 되므로, 동일한 객체의 모든 `synchronized` 블록은 **동시에 한 스레드만 실행**할 수 있습니다.  
`synchronized` 키워드는 `메서드 앞`에 붙이거나 `코드 블럭` 중간에 사용할 수 있습니다.  
### 키워드 위치에 따른 의미
#### `인스턴스 메서드`
```java
public synchronized void synchronisedCalculate() {
    setSum(getSum() + 1);
}
```
메서드 선언 앞에 `synchronized` 키워드를 붙임으로써 해당 메서드가 동기화 처리 되도록 합니다.  
**메서드를 소유한 인스턴스에 의해 동기화**되므로, **한 인스턴스 당 하나의 스레드**만 해당 메서드를 실행할 수 있습니다.  
#### `정적 메서드`
```java
 public static synchronized void syncStaticCalculate() {
     staticSum = staticSum + 1;
 }
```
정적 메서드 앞에 `synchronized` 키워드를 붙임으로써 해당 메서드가 동기화 처리 되도록 합니다.  
해당 메서드를 소유한 클래스의 **`Class` 객체에 의해 동기화**됩니다. 클래스 별로 **JVM 당** 하나의 `Class` 객체만 존재하므로 인스턴스 갯수에 상관없이 **하나의 스레드만 해당 메서드를 실행**할 수 있습니다.  
#### `코드 블록`
```java
// 인스턴스 메서드 내 코드 블록 synchronized
public void performSynchronisedTask() {
    synchronized (this) {
        setCount(getCount()+1);
    }
}
```
메서드 중간에 `synchronized (obj)` 키워드를 사용하므로써 **코드 일부를 동기화** 할 수 있습니다.  
`synchronized`에 **매개변수**로 전달되는 객체는 **`모니터` 객체**로, 블록 내부의 코드는 해당 객체에서 동기화됩니다. 따라서, `모니터` 객체 당 하나의 스레드만 해당 코드 블록 내의 작업을 실행할 수 있습니다.  
*`정적 메서드`의 경우*, 매개변수로 전달되는 **`Class` 객체**에 의해 동기화 됩니다.  
```java
// 정적 메서드 내 코드 블록 synchronized
public static void performStaticSyncTask(){
    synchronized (SynchronisedBlocks.class) {
        setStaticCount(getStaticCount() + 1);
    }
}
```
### `재진입성(Reentrancy)`
스레드가 *락을 소유하고 있는 동안*, 해당 **락을 다시 획득**할 수 있습니다.  
이렇게 한 스레드가 **같은 락을 두 번 이상 획득**할 수 있도록 허용하게 되면 `재진입 동기화(reentrant synchronization)`가 가능합니다.  
```java
Object lock = new Object();
synchronized (lock) {
    System.out.println("First time acquiring it");

    synchronized (lock) {
        System.out.println("Entering again");

         synchronized (lock) {
             System.out.println("And again");
         }
    }
}
```
즉, `synchronized` 코드가 직접/간접적으로 `synchronized` 코드가 포함된 메서드를 호출하고, **두 코드가 같은 락을 사용**하는 상황이 가능하게 됩니다.  
이러한 재진입 동기화 없이는 `synchronized` 코드는 스레드 스스로를 차단하지 않도록 추가적인 예방 조치를 취해야합니다.  
## 효율적인 코드 작성 측면에서, `synchronized`는 좋은 키워드일까?
`synchronized` 키워드는 공유 자원에 대한 여러 스레드의 접근을 제어해 간편하게 동기화 처리가 가능한 유용한 키워드지만, 아래와 같은 단점이 존재합니다.  
### 성능 저하
락을 사용해 한 번에 한 스레드만 코드 블록(또는 메서드 전체)에 접근 가능하므로 다른 스레드들은 해당 코드 블록의 모니터가 필요한 다른 메서드들을 실행하지 못하고 대기해야 합니다. 따라서 멀티-스레드 환경의 이점인 **동시성을 잃습니다**.  
또한,  `synchronized` 코드 실행을 기다리는 동안 블록된 스레드는 중단(interrupt)될 수 없습니다.  
### 데드락
**락을 기반**으로 동기화를 처리하므로, 둘 이상의 스레드가 락을 획득하고 서로의 락을 얻기위해 **무한히 대기**하는 **데드락이 발생할 수 있습니다.**  
### 예상치 못한대로 동작할 수 있음
락을 기반으로 하므로, *락의 기준으로 설정되는 객체에 따라*  **코드가 예상한대로 동작하지 않을 수** 있습니다.  
예를 들어 인스턴스 메서드 M1, M2에 `synchronized` 키워드를 사용했고, 스레드 A,B 가 동작중인 상황이라면 스레드 A가 M1 메서드를 수행하는 동안, B는 *M1 메서드 뿐만 아니라* **M2 메서드에도 진입할 수 없습니다.**  
- 이는 **`synchronized` 인스턴스 메서드** 의 락이 **인스턴스를 기준**으로 하기 때문에 동일한 인스턴스에 대해서 메서드들은 이미 한 `synchronized` 메서드가 실행 중이라면, 다른 `synchronized` 메서드 역시 수행할 수 없습니다.
## `synchronized`이외의 다른 동기화 기법
Java에서는 `synchronized` 키워드 이외에도 동기화를 처리할 수 있는 방법들을 제공하고 있습니다.
### `락 객체(Lock Object)`
락 객체는 기존의 `synchronized` 블록에서 사용하던 `모니터`와 유사하지만 보다 **정교한 형태**입니다.  
`모니터`와 마찬가지로 한 번에 한 스레드만 `Lock` 객체를 소유할 수 있으며, `Lock` 객체는 연결된 `Condition` 객체를 통해 `대기/알림(wait/notify)` 메커니즘도 지원합니다.  
- `대기/알림(wait/notify)`: 스레드가 처리할 수 있는 상태면 `notify()`를 통해 Runnable 상태로, 그렇지 않다면 `wait()`을 통해 대기 상태로 변경하는 방식.
  
락 객체를 사용하면, 기존의 `모니터`와 달리 락을 획득할 수 없는 경우 **다른행동을 할 수 있다**는 장점이 있습니다. 
```java
...
static class Friend {
	private final Lock lock = new ReentrantLock();
	...
	// Lock을 얻고자 시도하는 함수
	public boolean impendingBow(Friend bower) {
		Boolean myLock = false;
		Boolean yourLock = false;
		try {
      // 락 획득 시도
			myLock = lock.tryLock();
			yourLock = bower.lock.tryLock();
		} finally {
			if (! (myLock && yourLock)) {
				if (myLock) {
					lock.unlock();
				}
				if (yourLock) {
					bower.lock.unlock();
				}
			}
		}
		return myLock && yourLock;
	}
	// 메인 행동 함수
	public void bow(Friend bower) {
		if (impendingBow(bower)) {
      // 락 획득에 성공한 경우
			try {
				System.out.format("%s: %s has"
					+ " bowed to me!%n", 
					this.name, bower.getName());
				bower.bowBack(this);
			} finally {
				lock.unlock();
				bower.lock.unlock();
			}
		} else {
      // 락 획득에 실패한 경우
			System.out.format("%s: %s started"
				+ " to bow to me, but saw that"
				+ " I was already bowing to"
				+ " him.%n",
				this.name, bower.getName());
		}
	}
```
`java.util.concurrent.locks` 패키지에 인터페이스인 `Lock`은 물론 `ReentrantLock`와 같은 구현체도 존재합니다.  
### `Volatile`
*공유 메모리 환경*에서 발생할 수 있는 **`캐시 일관성`** 문제를 해결해주는 키워드입니다.
변수에 해당 키워드를 달게되면, *변수에 대한 모든 수정*이 **즉시** 다른 스레드에게 **전파**됩니다. 이는 `Volatile` 키워드가 달린 변수는 *CPU 캐시가 아닌* **메인 메모리에서 읽고 쓰기** 때문에 가능합니다.
```java
private volatile static int number;
```
`synchronized` 코드 블록과 달리, 한 번에 *여러 스레드가 코드 블록을 실행해도* **변수의 가시성을 보장**할 수 있습니다.
### `원자 변수(Atomic variable)`
[`java.util.concurrent.atomic`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html) 패키지 내에 단일 변수에 대한 원자 연산을 지원하는 클래스들이 정의되어 있습니다.  
`AtomicBoolean`, `AtomicInteger` 등 **원시 타입에 대응**되는 원자 변수는 물론, `AtomicReference` 와 같이 **객체에 대응**되는 원자 변수도 존재합니다.  
클래스들은 `volatile` 변수에 대한 읽기/쓰기처럼 동작하는 `get()`/`set()`를 갖고 있습니다.  
따라서, `set()`은 같은 변수에 대한 모든 이후의 `get()` 호출에 대해 `발생-전 관계(happens-before Relationship)` 를 갖습니다.  
```java
import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {
    private AtomicInteger c = new AtomicInteger(0);

    public void increment() {
		// Atomically increments by one the current value.
        c.incrementAndGet();
    }

    public void decrement() {
		// Atomically decrements by one the current value.
        c.decrementAndGet();
    }

    public int value() {
        return c.get();
    }

}
```

## ThreadLocal이란?
`java.lang` 패키지에 존재하는 [클래스](https://docs.oracle.com/javase/8/docs/api/java/lang/ThreadLocal.html)로써, 이를 사용하면 *각 스레드 별로* **스스로만 접근할 수 있는 데이터**를 저장할 수 있습니다.
```java
// 먼저, 값을 저장할 ThreadLocal 인스턴스를 생성합니다.
ThreadLocal<타입> threadLocalValue = new ThreadLocal<>();

// get(), set() 메서드를 통해 값을 읽고, 쓸 수 있습니다.
threadLocalValue.set(1);
Integer result = threadLocalValue.get();

// remove() 메서드를 통해 값을 지울 수 있습니다.
threadLocalValue.remove();
```
### 주의사항
이를 ThreadPool과 함께 사용 할 경우, *ThreadLocal의 값을 지우지 않고* 현재 **스레드를 풀에 반납**했다 다시 사용하게 되면 **이전에 ThreadLocal에 저장한 값**이 남아있게 됩니다.  
이를 주의해서 사용할 필요가 있습니다.

# 참고 문서
- [java - Can a synchronized block/method be interrupted? - Stack Overflow](https://stackoverflow.com/questions/25461463/can-a-synchronized-block-method-be-interrupted)
- [synchronization - Disadvantage of synchronized methods in Java - Stack Overflow](https://stackoverflow.com/questions/1365880/disadvantage-of-synchronized-methods-in-java)
- [Java – Synchronized 주의점 | Routine (wordpress.com)](https://crackjamx.wordpress.com/2013/02/07/java-synchronized-%EC%A3%BC%EC%9D%98%EC%A0%90/)
- [Guide to the Synchronized Keyword in Java | Baeldung](https://www.baeldung.com/java-synchronized)
- [An Introduction to ThreadLocal in Java | Baeldung](https://www.baeldung.com/java-threadlocal)
- [Guide to the Volatile Keyword in Java | Baeldung](https://www.baeldung.com/java-volatile)
- [Bad Practices With Synchronization | Baeldung](https://www.baeldung.com/java-synchronization-bad-practices)
- [Thread의 개인 수납장 ThreadLocal (gmarket.com)](https://dev.gmarket.com/62)
- [Intrinsic Locks and Synchronization (The Java™ Tutorials > Essential Java Classes > Concurrency) (oracle.com)](https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html)
- [Synchronized Methods (The Java™ Tutorials > Essential Java Classes > Concurrency) (oracle.com)](https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html)
- [Atomic Variables (The Java™ Tutorials > Essential Java Classes > Concurrency) (oracle.com)](https://docs.oracle.com/javase/tutorial/essential/concurrency/atomicvars.html)
- [Lock Objects (The Java™ Tutorials > Essential Java Classes > Concurrency) (oracle.com)](https://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html)
- [Happens-Before Relationship in Java - GeeksforGeeks](https://www.geeksforgeeks.org/happens-before-relationship-in-java/)
- [Inter-thread Communication in Java - GeeksforGeeks](https://www.geeksforgeeks.org/inter-thread-communication-java/)
- [Synchronization in Java - GeeksforGeeks](https://www.geeksforgeeks.org/synchronization-in-java/)

