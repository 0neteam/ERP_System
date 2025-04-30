<div align="center">

[<img src="UI/logo_black.png" width="100"/>](https://github.com/0neteam/ERP_System)

### ERP_System
[<img src="https://img.shields.io/badge/프로젝트 기간-2025.12.09~2025.12.09-green?style=flat&logo=&logoColor=white" />](https://github.com/0neteam/ERP_System)

</div> 

<br />

## 📌 개요
다수의 모듈로 구성된 사내 자원 관리 플랫폼으로,
제조(Manufacturer), 창고(Storage), 운송(Transportation) 등의 업무를 
마이크로서비스 아키텍처(MSA) 기반으로 통합 관리합니다.
각 모듈 간 API 게이트웨이와 인증 서버를 통해 유연한 확장성과 보안성을 보장합니다.

- 프로젝트 개요
- 프로젝트 팀원
- 사용한 기술 스택
- 저장소 구조
- 프로젝트 아키텍처
- 개발 워크플로우
- 설치 및 실행
- API 문서
- 화면 구성
- 주요 기능

<br />

## 💁‍♂️ 프로젝트 팀원
| 팀장 | 팀원 | 팀원 | 팀원 |
|:-----:|:--------:|:---------:|:------:|
| [<img src="https://github.com/HelenHam.png" width="80" alt="함효신"/>](https://github.com/HelenHam) | [<img src="https://github.com/GGobung-of-Simba.png" width="80" alt="임지은"/>](https://github.com/GGobung-of-Simba) | [<img src="https://github.com/swparabellum.png" width="80" alt="강승우"/>](https://github.com/swparabellum) | [<img src="https://github.com/KY129.png" width="80" alt="이경일"/>](https://github.com/KY129) |
| [함효신](https://github.com/HelenHam) | [임지은](https://github.com/GGobung-of-Simba) | [강승우](https://github.com/swparabellum) | [이경일](https://github.com/KY129) |

<br />

## ⚙ 기술 스택

### front-end
<div>
  <img src="https://github.com/user-attachments/assets/c4f1ad1b-009d-4c3e-9719-77c3584d3c36" width="80">
  <img src="https://github.com/user-attachments/assets/c8349feb-172d-4b7a-9317-fe1d701c6af1" width="80">
  <img src="https://github.com/user-attachments/assets/71be7c9c-6d7d-4659-b7c8-d9d792c806b9" width="80">
  <img src="https://github.com/user-attachments/assets/0851b258-ed10-4893-9c8e-86baa09f607b" width="80">
  <img src="https://github.com/user-attachments/assets/ec60a747-ce50-4635-9ccf-ef6f45d4323d" width="80">
</div>

### Back-end
<div>
  <img src="https://github.com/user-attachments/assets/cf7a2b5e-1029-4208-9baf-84197c30f364" width="80">
  <img src="https://github.com/user-attachments/assets/c6b3cecd-7659-45d1-99d5-152bbefea166" width="80">
  <img src="https://github.com/user-attachments/assets/01297ea8-bb8d-4ec2-b63f-cfaab9d64ddd" width="80">
  <img src="https://github.com/user-attachments/assets/d1cf27e6-dd9d-457f-8101-7c53baab3caa" width="80">
  <img src="https://github.com/user-attachments/assets/850fb75f-c138-49ae-9f27-9cfe135fd6f3" width="80">
  <img src="https://github.com/user-attachments/assets/231e0131-8749-45b9-9a9e-5b8765755489" width="80">
  <img src="https://github.com/user-attachments/assets/a86b996c-d622-461c-a021-8650fa9b7cff" width="80">
</div>

### Infra & Tools
<div>
  <img src="https://github.com/user-attachments/assets/f69158e3-9425-4081-b59c-ac9749cc4161" width="80">
  <img src="https://github.com/user-attachments/assets/d282e7db-5223-42e9-b515-8333bdd5830f" width="80">
  <img src="https://github.com/user-attachments/assets/6809e8b0-41d7-4dee-85c4-68ec5baa2f3d" width="80">
  <img src="https://github.com/user-attachments/assets/336ef6d3-9a44-4579-9f36-e3bb41b072f8" width="80">
</div>

<br />

## 📁 저장소 구조
```
/ERP_System
├── .idea                 # IntelliJ 설정 파일
├── UI                    # 공통 UI 컴포넌트 및 정적 리소스
├── gateway               # API Gateway 서비스 (Spring Cloud Gateway)
├── mfr                   # 제조사(Manufacturer) 서비스 모듈
├── oauth                 # 인증 서버 (Spring Authorization Server, JWT)
├── stg                   # 창고(Storage) 서비스 모듈
├── trs                   # 운송(Transportation) 서비스 모듈
└── view                  # Front-end 애플리케이션 (React, Vite)
```

<br />

## 🛠️ 프로젝트 아키텍처
![no-image](https://user-images.githubusercontent.com/80824750/208294567-738dd273-e137-4bbf-8307-aff64258fe03.png)

<br />

## 🛠️ 개발 워크플로우
### 브랜치 전략 (Branch Strategy)
- main
  - 배포 가능한 상태의 코드를 유지하는 메인 브랜치로, 직접 개발하지 않습니다.

- dev
  - 모든 팀원이 개발을 진행하는 기본 브랜치입니다.
  
- 각 맡은 파트{name}
  - 팀원 각자의 개발 브랜치입니다.
  - 모든 기능 개발은 이 브랜치에서 이루어집니다.

<br />

## 🚀 설치 및 실행

### 요구 사항
- Java 17 이상

- Maven 3.6+

- Node.js 16 이상

- npm 8+

- MariaDB 10.5 이상

- Git 2.20 이상

### 1. 저장소 클론
```
git clone https://github.com/0neteam/ERP_System.git
cd ERP_System
```

### 2. 공통 환경 변수 설정
- 각 모듈 최상위에 .env 파일로 환경변수 설정
#### Gateway (.env)
```
# 허용할 CORS Origin
CORS_ORIGIN1=http://localhost:5173
CORS_ORIGIN2=http://127.0.0.1:5173

# 라우팅 URI
ROUTE_STG_URI=http://localhost:8001
ROUTE_MFR_URI=http://localhost:8002
ROUTE_TRS_URI=http://localhost:8003
ROUTE_OAUTH_URI=http://localhost:9000
ROUTE_API_URI=http://localhost:8000
ROUTE_REACT_URI=http://localhost:5173
```

#### Auth (인증 서버, .env)
```
# Database 설정
DB_DRIVER=org.mariadb.jdbc.Driver
DB_JDBC=jdbc:mariadb://<DB_HOST>:<DB_PORT>/<DB_NAME>
DB_USER=<DB_USERNAME>
DB_PWD=<DB_PASSWORD>

# Swagger URL
SWAGGER_URL=http://localhost:8000

# RSA 키 파일 경로
RSA_PUBLIC_KEY=classpath:keys/public.pem
RSA_PRIVATE_KEY=classpath:keys/private.pem

# 이메일 SMTP 설정
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=<YOUR_EMAIL>
EMAIL_PASSWORD=<YOUR_EMAIL_PASSWORD>
EMAIL_SMTP_AUTH=true
EMAIL_SMTP_TIMEOUT=5000
EMAIL_SMTP_ENABLE=true
EMAIL_SMTP_SSL=true
```
- 키 파일 위치: oauth/src/main/resources/keys/ 폴더에 public.pem 및 private.pem을 저장하세요.

#### Services (mfr, stg, trs, .env)
```
# Database 설정
DB_DRIVER=org.mariadb.jdbc.Driver
DB_JDBC=jdbc:mariadb://<DB_HOST>:<DB_PORT>/<DB_NAME>
DB_USER=<DB_USERNAME>
DB_PWD=<DB_PASSWORD>

# Swagger 및 JWT 키 URL
SWAGGER_URL=http://localhost:8000
JWT_KEY_URL=http://localhost:8000/oauth/.well-known/jwks.json
```

#### Front-End (view, .env)
```
VITE_APP_GATEWAY_URL=http://localhost:8000
```

### 3. 모듈별 실행
#### Back-End 예시: 제조 모듈(mfr)
```
cd mfr
mvnw spring-boot:run
또는
MfrApplication.java 우클릭 → Run
```

#### Front-End
```
cd view
npm install
npm run dev
```
- 개발 서버: http://localhost:5173

<br />

## 📑 API 문서
- 각 서비스 모듈별 Swagger UI
   - http://localhost:8000/swagger-ui/index.html
<img src="https://github.com/user-attachments/assets/46a0be6f-aed8-4bd1-b35d-609707b22dd9" width="400"/>

<br />


## 🖼️ 화면 구성
|메인 화면|발주 상세 화면|
|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/573151ce-b278-438f-9984-b0118467b5d2" width="400"/>|<img src="https://github.com/user-attachments/assets/91b932b7-72b8-4671-af26-8fa195895289" width="400"/>|

<br />

## 🎯 주요 기능

- **이메일 인증**: 회원가입 및 로그인 시 인증 코드 발송 기능

- **발주 및 출고 관리**: 발주 추가/조회/검색, 출고 요청, 모달 상세 정보 제공

- **재고 관리**: 입출고 내역, 재고 수량, 기간별 활용도 분석

- **운송 관리**: 운송 요청 승인, 기사(Driver) 배정, 운송 상태 실시간 업데이트

- **검색 및 필터링**: 발주, 재고, 운송, 입출고 내역 등 다양한 카테고리별 검색 기능

- **모듈화된 인증·인가**: Spring Authorization Server 기반 JWT 인증, 서비스별 권한 설정

<br />
