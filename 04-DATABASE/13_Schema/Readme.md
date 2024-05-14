# Schema

Schema의 3계층에 대해 설명해 주세요.

### Schema

> DB의 구조와 제약 조건에 관한 전반적인 명세를 정의한 메타데이터의 집합
>
> 스키마는 대부분의 DBMS에서 사용하는 ANSI/SPARC모델의 three-schema architecture에 따라 외부 스키마, 개념 스키마, 내부 스키마로 구분한다

> three-schema acrchitecture의 주요 목적
>
> - 사용자마다 동일데이터에 다른 보기가 필요함
> - 특정 사용자가 데이터를 확인해야 하는 접근방식은 시간에 따라 다를 수 있음
> - 사용자는 DB의 물리적 구현 및 내부 작동에 신경을 쓸 필요가 없음
> - 모든 사용자는 자신의 요구 사항에 따라 동일한 데이터에 액세스할 수 있어야 함
> - DBA는 사용자의 작업에 영향을 주지 않고 데이터베이스의 개념적 구조를 변경할 수 있어야 함
> - 데이터베이스의 내부 구조는 스토리지의 물리적 측면 변경으로 인해 영향을 받지 않아야 함

- Entity(개체)

  - 데이터로 표현하려는 객체

- Attribute(속성)

  개체가 가지는 속성

- Specification(명세)

  개체가 가지는 속성

### External Schema(외부 스키마)

    - 사용자들의 집장에서의 데이터베이스의 논리적 구조
    - 여러개의 외부 스키마가 존재 가능

### Conceptual Schema(개념 스키마)

    - DB의 전체 조직에 대한 논리적 구조
    - 한개만 존재

### Internal Schema(내부 스키마)

    - 물리적 저장장치에서 본 DB 구조
    - Conceptual Schema를 물리적으로 표현하기 위한 방법 기술
    - 저장될 데이터 항목의 내부 레코드 형식, 물리적 순서 등을 나타낸다

---

http://terms.tta.or.kr/dictionary/dictionaryView.do?subject=%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4+%EC%8A%A4%ED%82%A4%EB%A7%88

https://www.javatpoint.com/dbms-three-schema-architecture
