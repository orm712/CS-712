# Garbage Collection

### 본인이 사용하는 언어에서는 GC를 어떻게 구현했나요?

#### Java Garbage Collection

1.  Marking

    <img src="./marking.png" width="75%">

    > 사용하는 메모리 조각과 사용하지 않는 메모리 조작을 식별한다.<br>

2.  Deletion

    - Normal Deletion

    <img src="./nomal_deletion.png" width="75%">

    > 참조되지 않은 객체를 제거하고 메모리 할당자는 새 개체를 할당할 수 있는 여유 공간 블록의 참조를 보유한다.

    - Deletion with Compacting

    <img src="./deleton_with_compacting.png" width="75%">

    > 참조된 나머지를 압축하여 개체를 이동하여 새로운 메모리 할당을 쉽게 한다.<br>
    > 사용중인 클래스를 기반으로 런타임시 JVM에 의해 채워진다.

    ***

    JVM Generations

    <img src="./hotspot_heap_structure.png" width="75%">

    1. Young Generation<br>
       > 모든 새로운 객체가 할당되고 노화되는 곳
       > 가득차게 되면 `minor garbage collection`이 발생한다
    2. Old Generation<br>
       > 오래 살아남는 객체(참조가 계속되는)를 저장한느데 사용된다.<br>
       > Young Generation에서 임계값을 정하고 Age가 충족되면 넘어온다.
    3. Permanent Generation<br>
       > JVM이 App에 사용되는 클래스와 메소드를 설명하는 메타데이터가 포함되어있다.

`minor GC(garbage collection)` - Stop the World event로 이 작업이 모두 완료될 때까지 모든 App 스레드가 중지된다

### GC의 장단점에 대해 설명해 주세요.

### GC는 어떤 영역에 있는 데이터를 관리하나요?

### Reference Counting 방식에 대해 설명하고, 이 알고리즘에서 발생할 수 있는 순환 참조 및 Retain Cycle에 대해 설명해 주세요.

https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html

https://woooongs.tistory.com/51

https://mangkyu.tistory.com/118
