# 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼 - ZgZg Express 

<img src="https://github.com/user-attachments/assets/e8ffe057-f2e7-400d-a1e4-86910b4cdeb1" alt="서비스 소개" width="100%"/>


<br/>
<br/>

# Getting Started (서비스 구성 및 실행 방법)
![image](https://github.com/user-attachments/assets/7c9a9e45-2b24-441c-8f20-da6894a7078e)
![image](https://github.com/user-attachments/assets/9e937597-712b-4279-ad41-e6ff25d48399)
![image](https://github.com/user-attachments/assets/872337eb-c0e6-4df0-a342-a6ede1b67452)
![image](https://github.com/user-attachments/assets/742483a1-4a9d-4b17-919c-52c4072f4bdb)
![image](https://github.com/user-attachments/assets/35b89d13-bdd2-49a4-87e8-cbe25865a541)
![image](https://github.com/user-attachments/assets/7733c11f-41bb-4e38-acbb-ac5b52a7bc87)
![image](https://github.com/user-attachments/assets/eb3b7164-d778-4b32-9ba5-df0c2e9e6951)
![image](https://github.com/user-attachments/assets/9420be13-bc06-4674-9810-de4c64e4ac7e)
![image](https://github.com/user-attachments/assets/9fc17597-4cee-4a74-873b-683d5a25cc10)
![image](https://github.com/user-attachments/assets/1496188e-2171-4033-af6f-f1b621fe43ac)


<br/>
<br/>

# About Project
- 프로젝트 목적: 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
- 프로젝트 설명: 물류 관리 및 배송 시스템입니다. MSA기반 플랫폼으로, 대규모 트래픽을 고려한 설계와 개발을 진행하였습니다.
- 아키텍처 설명 : 허브, 배송담당자, 회원, 주문, 업체, 상품, 배송, slack/AI로 나뉘어져 있으며, DB, SpringBoot 모두 docker container로 관리 하였습니다. 서비스 간의 통신은 FeignClient를 통해서 진행하였습니다.
<br/>

![image](https://github.com/user-attachments/assets/6c3093f4-e6b2-41c2-b57d-bd7a7eab9347)

<br/>
<br/>


## 적용 기술

### Redis

- 빈번하게 조회되는 데이터를 캐싱
- 허브의 경로 데이터는 경로를 계산하여 Cache-Aside 전략으로 캐싱

### QueryDSL

- 정렬, 권한 별 조회 등에 따른 동적 쿼리 작성을 위하여 QueryDSL을 도입하여 활용했습니다.

### Swagger

- 각각의 모듈과 정확한 소통을 위해 스웨거를 통해 API 를 관리하였습니다.

### Zipkin

- 성능 문제를 분석하고 트랜잭션 추적을 위해 Zipkin을 도입하였습니다.
- 마이크로서비스 간의 호출 관계를 시각화하여 성능 병목 지점을 식별할 수 있도록 하였습니다.

### Aop 로깅

- 공통적으로 적용해야 하는 로깅 기능을 AOP(Aspect-Oriented Programming)를 활용하여 구현하였습니다.
- 서비스 레이어 및 주요 비즈니스 로직에서 실행 시간을 측정하고 로깅하여 성능을 모니터링하였습니다.


<br/>
<br/>


## 트러블 슈팅

- [Spring Security를 도입하며 발생한 의존성 문제](https://github.com/High-Quality-Coffee/JGJW-Express/wiki/spring-security%EB%A5%BC-%EB%8F%84%EC%9E%85%ED%95%98%EB%A9%B0-%EB%B0%9C%EC%83%9D%ED%95%9C-%EC%9D%98%EC%A1%B4%EC%84%B1-%EB%AC%B8%EC%A0%9C)
- [Socket hang up](https://github.com/High-Quality-Coffee/JGJW-Express/wiki/socket-hang-up)
- [Redis와 관련된 직렬화 문제](https://github.com/High-Quality-Coffee/JGJW-Express/wiki/Redis%EC%99%80-%EA%B4%80%EB%A0%A8%EB%90%9C-%EC%A7%81%EB%A0%AC%ED%99%94-%EB%AC%B8%EC%A0%9C)


<br/>
<br/>


## 고민

**— 기술/구현**

- [MSA 에서의 인증/인가 처리 방식](https://github.com/High-Quality-Coffee/JGJW-Express/wiki/MSA-%EC%97%90%EC%84%9C%EC%9D%98-%EC%9D%B8%EC%A6%9D-%EC%9D%B8%EA%B0%80-%EC%B2%98%EB%A6%AC-%EB%B0%A9%EC%8B%9D)

- [Hub 데이터 저장 구조](https://github.com/High-Quality-Coffee/JGJW-Express/wiki/Hub-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%A0%80%EC%9E%A5-%EA%B5%AC%EC%A1%B0)


**— 피드백**

- [주문과 배송의 관계](https://github.com/High-Quality-Coffee/JGJW-Express/wiki/%EC%A3%BC%EB%AC%B8%E2%80%90%EB%B0%B0%EC%86%A1-%EA%B4%80%EA%B3%84)


<br/>
<br/>

# Key Features (주요 기능)
- **회원**:
  - 권한별 회원가입을 진행합니다
  - 권한은 MASTER(관리자), HUB(허브담당자), STORE(업체담당자), DELIVERY(배송담당자)로 분류되어 있습니다
  - 로그인이 진행되고, 필터 단에서 유저의 권한을 확인하여 토큰을 발급합니다.
  - 발급된 토큰을 기반으로 gateway의 AuthPermissionFilter에서 권한별 API를 처리합니다.

- **배송담당자**:
  - 스파르타 물류센터에 속한 허브 배송담당자가 10명 존재합니다
  - 각 허브별 업체 배송담당자는 10명이 존재합니다
  - Redis Queue를 통해서 배송 담당자의 순번에 따라서(라운드 로빈방식으로) 배송담당자를 할당합니다.

- **주문**:
   
  
- **배송**:
  

- **상품**:
  - 상품 신규 등록이 가능합니다
  - 상품 재고 추가/감소가 가능합니다
  - 주문 서비스 호출을 통해서 재고 추가/감소를 진행합니다.

- **허브**:
  
    
 
- **업체**:
  
  
- **SLACK/AI**
  

<br/>
<br/>


# ERD
<br/>
<br/>

![image](https://github.com/user-attachments/assets/06447762-be35-440a-99c9-4e900c806151) <br/>
![image](https://github.com/user-attachments/assets/f6bb5cc1-5be0-46ba-b229-58a3710ddba8) <br/>
![image](https://github.com/user-attachments/assets/7eb92a53-849e-4dc5-8200-37fdb2cf122c) <br/>


<br/>
<br/>


# Technology Stack (기술 스택)

## BackEnd
|  |  |  |
|-----------------|-----------------|-----------------|
| SpringBoot  |  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" alt="SpringBoot" width="200"> | 3.4.3    |
| Java  |  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white" alt="Java" width="200" > | 17 |
| Spring Data JPA  |  <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=SpringDataJPA&logoColor=white" alt="JPA" width="200" >    | 5.0.0  |
| QueryDSL  |  <img src="https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logo=QueryDSL&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200" >    | 1.11.12    |
| Spring Security |  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200">    | 3.4.2    |

<br/>

## Infra
|  |  |  |
|-----------------|-----------------|-----------------|
| PostgreSQL  |  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white" alt="PostgreSQL" width="200">    | 16.3 |
| pgAdmin    |  <img src="https://img.shields.io/badge/pgAdmin-4169E1?style=for-the-badge&logo=pgAdmin&logoColor=white" alt="pgAdmin" width="200">    | latest  |
| Docker  |  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white" alt="docker" width="200">    |  |

<br/>
<br/>

# Development Workflow (개발 워크플로우)
## 브랜치 전략 (Branch Strategy)
우리의 브랜치 전략은 아래와 같은 Git Flow를 기반으로 하며, 다음과 같은 브랜치를 사용합니다.

<img src="https://github.com/user-attachments/assets/f683ef08-c485-4447-a1d8-a5c6468e093a" alt="git flow" width="500">


- Main Branch
  - 배포 가능한 상태의 코드를 유지합니다.
  - 모든 배포는 이 브랜치에서 이루어집니다.
  
- develop Branch
  - 통합 기능 관리 브랜치 입니다
  - feat에서 개발한 기능을 develop 브랜치에서 통합하여 관리합니다.
 
- feat Branch
  - 기능 개발 브랜치 입니다.
  - 기능 단위로 브랜치를 나누어 기능을 개발하였습니다.
 
- refactor Branch
  - 코드 리팩토링 브랜치 입니다.
  - 코드 리팩토링이 필요한 경우 refactor 브랜치에서 작업했습니다.
 
- release Branch
  - 배포 전 버전을 관리하는 브랜치 입니다.
  - 최종 배포하기 전 테스트를 진행하고, 이상이 없다면 Main브랜치로 배포를 진행합니다.
 
- hotfix Branch
  - 핫픽스를 관리하는 브랜치 입니다.
  - 배포된 환경에서 수정사항이 발생했을 경우, hotfix 브랜치에서 관리하였습니다.

<br/>
<br/>


# Members (팀원 및 팀 소개)
<table style="margin-left:auto;margin-right:auto;">
  <tr height="160px">
    <th align="center" width="140px">
      <a href="https://github.com/High-Quality-Coffee"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/125748258?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/Ryujy"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/63836145?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/sosa7753"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/141195262?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/singingsandhill"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/64348312?v=4"/></a>
    </th>
  </tr>
  <tr>
    <td align="center" width="160px">
      <a href="https://github.com/High-Quality-Coffee"><strong>박규원</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/Ryujy"><strong>류지윤</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/sosa7753"><strong>박상욱</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/singingsandhill"><strong>김지수</strong></a>
    </td>
  </tr>
  <tr>
    <td align="center" width="160px">
      회원, 배송담당자, 상품
    </td>
    <td align="center" width="160px">
      주문, 배송
    </td>
    <td align="center" width="160px">
      허브
    </td>
    <td align="center" width="160px">
      슬랙, 업체
    </td>
  </tr>
  <tr>
    <td align="center" width="160px">
       Lead
    </td>
    <td align="center" width="160px">
       Tech Lead
    </td>
    <td align="center" width="160px">
       BE
    </td>
    <td align="center" width="160px">
       BE
    </td>
  </tr>
</table>
