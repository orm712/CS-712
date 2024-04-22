OAuth가 무엇인지 설명하고, 이것은 인증인지 인가인지에 대해 설명해 주세요.

# Authentication(인증) & Authorization(인가)

## Authentication(인증)

> 로그인과 같이 사용자 또는 프로세스의 신원을 확인하는 프로세스

`인증`

|  Factor  | 설명                              | 예                 |
| :------: | --------------------------------- | ------------------ |
| 지식기반 | 사용자만 알고 있는 것             | 패스워드, 주민번호 |
| 소유기반 | 사용자만 소유하고 있는 것         | 인증서, OTP        |
| 속성기반 | 생체기반으로 사용자만의 고유 속성 | 지문, 홍채         |

비밀번호 없는 인증

- 매직링크 또는 이메일 또는 문자 메시지를 통해 전달된 OTP로 인증

2FA(Two Factor Authentication)/ MFA(Multi Factor Authentication)

- 2단계 또는 다중 요소 인증을 사용

SSO(Single Sign On)

- 단일 자격 증명 세트로 여러 응용 프로그램에 액세스 할 수 있음

Social Authentication

- 소셜 네트워킹 플랫폼의 기존 자격 증명으로 확인하고 인증

API Authentication

- 서버에서 서비스에 엑세스하려는 사용자 식별을 인증하는 프로세스

생체 인증

- 홍채 지문, 얼굴 등의 생체 데이터로 인증

---

## Authorization(인가)

> 누가 무엇을 할 수 있는지 결정하는 규칙

- 권한 부여는 사용자의 신원이 성공적으로 인증된 후 발생한다

`인증`

역할 기반 엑세스 제어, JSON 웹 토큰, SAML, OpenID 권한 부여,OAuth

https://baek.dev/post/24/
