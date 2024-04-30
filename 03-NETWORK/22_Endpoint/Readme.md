# Endpoint

## Networking Endpoint

- 컴퓨터 네트워크에 연결되어 정보를 교환하는 물리적 장치

> 데스크톱 컴퓨터, 스마트폰, 태블릿, 노트북, IoT 기기 등<br>
> 라우터, 스위치 등의 인프라 장치는 엔드포인트로 간주되지 않는다

### 공격

1. 피싱

2. 바이러스, 트로이목마 등등

3. DDos(Distributed-denial-of-service) (DDoS)

> ▷ 이외에에도 여러 공격 방법이 많음

### 보안

#### EPP(Endpoint Protection Platform)

- 관리자가 컴퓨터, 모바일 장치, 서버 및 연결된 장치를 포함한 모든 엔드포인트에서 사고를 모니터링, 보호, 조사 및 대응할 수 있는 중앙 집중식 관리 콘솔을 제공

## Communication Endpoint

- 통신 네트워크에서 데이터가 전송되거나 수신되는 지점

> 모뎀, 라우터, 스위치, 호스트 컴퓨터 등 TCP/IP 네트워크에 연결된 장치

## Web API Endpoint

### 정의

- 클라이언트가 서버에 요청을 보내는 지점을 API Endpoint, API Endpoint는 클라이언트가 API에 요청을 보내는 URL

## OAuth 2.0 Endpoint

인증 및 권한 부여 과정에서 사용되는 특정 URL

> Web API에 속해있는 개념

### 종류

- Authorization Endpoint

  사용자가 클라이언트 애플리케이션에 대한 권한을 부여하는 과정에서 사용, 사용자가 정보 입력하는 곳

- Token Endpoint

  클라이언트 애플리케이션이 인증 코드를 사용하여 액세스 토큰을 요청

- Revocation Endpoint

  사용자가 기존에 발급받은 토큰을 무효화

> 웹 애플리케이션에서의 엔드포인트는 일반적인 기능 및 데이터 접근을 위한 URL인 반면, OAuth 2.0에서의 엔드포인트는 인증 및 권한 부여 과정에서 사용되는 특정 URL

## AWS VPC(Virtual Private Cloud) Endpoint

### 종류

- Gateway Endpoint

  NAT device가 없어도 Amazon S3 및 DynamoDB에 안정적으로 연결할 수 있다

- Interface Endpoint

  AWS PrivateLink 기술을 사용하여 구성된다<br>
  VPC 내부에 전용 라우팅 테이블이 생성됩니다. Interface Endpoint를 생성하게 되면 AWS 서비스에 대한 ENI가 일반적으로 한 개 생성된다

> ENI(Endpoint Network Interface)
>
> - VPC 내부에서 전용 IP 주소를 사용하여 AWS 서비스와 통신하는 인터페이스

> AWS PrivateLink
>
> - VPC 간에 프라이빗 IP 주소를 사용하여 안전하게 연결할 수 있도록 해주는 서비스이다.<br>
> - AWS PrivateLink를 사용하면 인터넷을 거치지 않고 VPC 간에 안전하게 통신할 수 있다

- Gateway Loadbalancer Endpoint

  VPC나 계정 사이에서 인터넷 트래픽을 분산 시켜주는 Load Balancer 서비스이다<br>
  다중 VPC, 계정 간 통신이 가능 할 뿐만 아니라 security applicances를 제공하는 업체들이 보안 솔루션 관리가 유용하고, 인터넷에서 유입되는 트래픽들이 보안 어플라이언스를 거쳐 방화벽, IPS에서 조치 후 실제 서비스로 보내지기 때문에 보안성을 높일 수 있다는 장점이 있다

https://tech.cloud.nongshim.co.kr/2023/03/16/%EC%86%8C%EA%B0%9C-vpc-endpoint%EB%9E%80/
