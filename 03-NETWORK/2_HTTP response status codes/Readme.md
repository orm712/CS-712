# HTTP response status codes

- http 요청이 성공적으로 완료되었는지 여부를 나타냄

<code>
Informational responses(100~199) : 정보 교환 <br>
Successful responses  (200~299) : 성공<br>
Redirection messages(300~399) : 자료 위치 변경<br>
Client error responses(400~499) : 클라이언트 오류<br>
Server error responses (500~599) : 서버 오류
</code>

---

## 1. 401 (Unauthorized) 와 403 (Forbidden)은 의미적으로 어떤 차이가 있나요?

- 401 (Unauthorized)에서는 접근에 대한 자격 제시를 요청한다

```http
#초기 요청
GET /documents/tech-news HTTP/1.1
Host: www.example.re
```

```http
#응답
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Basic; realm=”Documents”
WWW-Authenticate: Mutual
```

```http
#재 요청
GET /documents/tech-news HTTP/1.1
Host: www.example.re
Authorization: Basic RXhhbXBsZTphaQ==
```

`ex)` 로그인을 하지 않고 로그인 된 사용자가 할 수 있는 일을 할때

- 403 (Forbidden)에서는 클라이언트에서 접근 권한 자체가 없다는 의미 이다

```http
#요청
GET /tech-news/confidential.pdf HTTP/1.1
Host: www.example.re
```

```http
#응답
HTTP/1.1 403 Forbidden
```

`ex)` 로그인은 했지만 권한 밖의 일을 할 때

---

## 2 . 200 (ok) 와 201 (created) 의 차이에 대해 설명해 주세요.

- 200(OK) 응답에서는 "성공"의 의미만 가지고 있습니다<br>

```http
HTTP/1.1 200 OK
Content-Type: application/pdf
Content-Length: 10000

<message body will contain the requested PDF document>
```

- 201(created) 응답에서는 "성공"의 의미에서 새로운 리소스가 생겼다는 것을 알려줍니다

```http
#요청
POST /incoming/xml HTTP/1.1
Host: www.example.re
Content-Type: application/xml
Content-Length: 104
<?xml version="1.0">
<article>
  <title>Test XML article</title>
  <author>Anonymous</author>
</article>
```

```http
# 응답
HTTP/1.1 201 Created
Location: /incoming/xml/article_1.xml
Content-Type: application/json
Content-Length: 19

{“success":"true"}
```

---

## 3. 필요하다면 저희가 직접 응답코드를 정의해서 사용할 수 있을까요? 예를 들어 285번 처럼요.

일반적으로는 HTTP 응답 상태 코드는 서버에서 자동으로 생성되고 특정 요청에 대한 적절한 상태코드를 반환하게 되어있다<br>
직접 코드를 작성해 직접 응답코드를 만들 수 있으나 표준화된 규약에 따라 정의된 HTTP 응답코드를 무시하고 작성을 한다면 통신의 의미를 혼동시킬 수 있고 다른 개발자들에 혼동을 줄 수 있으므로 사용하는 것을 권장하지 않는다
