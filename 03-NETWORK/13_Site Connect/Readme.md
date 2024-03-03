### www.github.com을 브라우저에 입력하고 엔터를 쳤을 때, 네트워크 상 어떤 일이 일어나는지 최대한 자세하게 설명해 주세요.
먼저, 웹브라우저에 naver.com을 검색하면 캐싱된 DNS기록들을 통해 해당 도메인 주소에 대응하는 IP주소를 확인합니다.

대응하는게 없다면, 웹브라우저가 HTTP를 사용하여 DNS에게 입력된 도메인 주소를 요청합니다.

이 과정에서 DNS query를 날리는데 name server들에게 .단위로 뒤에서부터 요청을 합니다. 예를 들어서 www.[naver.com](http://naver.com)이면 . 이후, .com , naver.com  www.naver.com 으로 매칭되는 IP주소를 찾게 됩니다. 이 과정을 Recursive Query라고 부릅니다.

DNS가 웹브라우저에게 찾는 사이트의 IP주소를 응답해주고, 웹브라우저가 웹서버에게 IP주소를 이용해서 GET요청으로 naver.com의 html웹페이지를 요청합니다.

이때, 요청 메시지는 TCP/IP프로토콜을 사용하고 3way handshake로 요청하게 됩니다.

이후, WAS에게 동적인 페이지와 데이터베이스 접근을 요청합니다.

위의 작업처리 결과를 웹서버로 전송하고, 웹서버는 웹브라우저에게 html문서결과를 응답하게 됩니다.

[https://velog.io/@eassy/www.google.com을-주소창에서-입력하면-일어나는-일](https://velog.io/@eassy/www.google.com%EC%9D%84-%EC%A3%BC%EC%86%8C%EC%B0%BD%EC%97%90%EC%84%9C-%EC%9E%85%EB%A0%A5%ED%95%98%EB%A9%B4-%EC%9D%BC%EC%96%B4%EB%82%98%EB%8A%94-%EC%9D%BC)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/3f3da6cc-eb5d-4e53-8160-d7257d98385f/2a12181f-d928-4b47-81a6-7a32c896f3c5/Untitled.png)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/3f3da6cc-eb5d-4e53-8160-d7257d98385f/c6f104a9-c14f-4741-8de7-25a3bde6b868/Untitled.png)

### 1. 사용자가 웹브라우저 검색창에 [www.google.com](http://www.google.com) 입력

### 2. 웹브라우저는 캐싱된 DNS기록들을 통해 해당 도메인 주소와 대응하는 IP주소를 확인

이 단계에서 캐싱된 기록에 없을 경우, 다음 단계로 넘어간다.

### 3. 웹브라우저가 HTTP를 사용하여 DNS에게 입력된 도메인 주소를 요청

### 4. DNS가 웹브라우저에게 찾는 사이트의 IP주소를 응답

ISP(Internet Service Provider)의 DNS서버가 호스팅하고 있는 서버의 IP주소를 찾기 위해 DNS query를 날린다.

- DNS query의 목적

  DNS 서버들을검색해서 해당 사이트의 IP주소를 찾는데에 있다.

  IP주소를 찾을 때까지 DNS서버에서 다른 DNS서버를 오가며 에러가 날 때까지 반복적으로 검색한다. = `recursive search`


`DNS recursor` (ISP의 DNS서버)는 `name server` 들에게 물어물어 올바른 IP주소를 찾는 데에 책임이 있다. `name server` 는 도메인 이름 구조에 기반해서 주소를 검색하게 되는데, 예를 들어 설명해보자면,

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/3f3da6cc-eb5d-4e53-8160-d7257d98385f/1b90c62a-3167-4e48-9831-904ae5b8c96c/Untitled.png)

```
'www.google.com' 주소에 대해 검색할 때,
1. DNS recursor가 root name server에 연락
2. .com 도메인 name server로 리다이렉트
3. google.com name server로 리다이렉트
4. 최종적으로 DNS기록에서 'www.google.com' 에 매칭되는 IP주소 찾기
5. 찾은 주소를 DNS recursor로 보내기
```

이 모든 요청들과 `DNS recursor`, IP주소는 작은 데이터 패킷을 통해 보내진다.

원하는 DNS기록을 가진 DNS서버에 도달할 때까지
클라이언트 ↔️ 서버를 여러번 오가는 과정을 거친다.

### 5. 웹브라우저가 웹 서버에게 IP주소를 이용하여 html문서를 요청

TCP로 연결이 되면, 브라우저는 GET요청을 통해 서버에게 www.google.com의 웹페이지를 요청한다.

### 6. 웹어플리케이션서버(WAS)와 데이터베이스에서 우선 웹페이지 작업을 처리

웹 서버 혼자서 모든 로직을 수행하고 데이터를 관리할 수 있다면 간단하겠지만, 그렇게 될 경우엔 서버에 과부하가 일어날 수 있다.

그렇기 때문에 서버의 일을 돕는 조력자 역할을 하는 것이 WAS이다.

WAS는 사용자의 컴퓨터나 장치에 웹어플리케이션을 수행해주는 `미들웨어`를 말한다.

브라우저로부터 요청을 받으면,

웹서버는 페이지의 로직이나 DB 연동을 위해 WAS에게 이들의 처리를 요청한다.

그러면 WAS는 이 요청을 받아 동적인 페이지처리를 담당하고, DB에서 필요한 데이터 정보를 받아서 파일을 생성한다.

📍 **웹서버와 웹어플리케이션서버(WAS)의 차이점**

- 웹서버 : 정적인 컨텐츠(HTML, CSS, IMAGE 등)를 요청받아 처리
- WAS : 동적인 컨텐츠(JSP, ASP, PHP 등)를 요청받아 처리=> DB서버에 대한 접속 정보가 있기 때문에 외부에 노출 될 경우 보안상의 문제를 이유로 웹서버와의 연결을 통해 요청을 전달받음

### 7. 위의 작업 처리 결과를 웹서버로 전송

### 8. 웹서버는 웹브라우저에게 html 문서결과를 응답

### 9. 웹브라우저는 화면에 웹페이지 내용물 출력
---
### DNS 쿼리를 통해 얻어진 IP는 어디를 가리키고 있나요?
### Web Server와 Web Application Server의 차이에 대해 설명해 주세요.
### URL, URI, URN은 어떤 차이가 있나요?