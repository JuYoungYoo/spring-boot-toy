# 음식점 예약 시스템 API 구현 : 비즈니스
---
- [x] 로그인/로그아웃 (매니저)
---
- [ ] 회원가입 (매니저)
- [ ] 매니저 내 정보 (비번변경, 회원탈퇴, 프로필(업로드 및 썸네일)) 
---
- [ ] 음식점 리스트, 페이징
- [ ] 음식점 정렬 (랭킹순, 리뷰개수순, 평점순 등)
- [ ] 음식점 필터 (음식점 카테고리(한식, 중식, 일식 등등))
- [ ] 음식점 검색 (이름)
---
- [ ] 예약 리스트 (유저)
- [ ] 예약 (유저) (시간 당, 매니저별 한명만 가능하다고 가정)
- [ ] 예약 수락 (매니저)
- [ ] 예약 거절 (매니저)
- [ ] 예약 수락을 20분 이상 하지 않을 시, 자동 거절
- [ ] 예약 취소 (유저)
--- 
- [ ] 요리사 랭킹 (리뷰개수와 평점으로 적절하게 랭킹설계)
- [ ] 리뷰 검색
- [ ] 리뷰 리스트 (리스트 페이징)
- [ ] 리뷰 조회수
- [ ] 리뷰 댓글 (매니저)
----











(공통)
- 잘못된 입력값 받을 시 Bad Request
- 불필요한 값은 무시한다.

- GET    : /session/new gets the webpage that has the login form
- POST   : /session authenticates credentials against database
- DELETE : /session destroys session and redirect to /

- GET   : /users/new gets the webpage that has the registration form
- POST  : /users records the entered information into database as a new /user/xxx
- GET   : /users/xxx // gets and renders current user data in a profile view
- POST  : /users/xxx // updates new information about user

# 회원가입 (유저, 요리사) : /users
1. 일반 사용자(USER) 계정 생성한다.
2. 요리사 계정 생성한다.


## 요구사항 
- Controller    
    - 회원가입 : user 
- Service
    - 이미 등록된 이메일은 회원가입 실패한다.
- Repository 
    - 계정 생성
    - 등록된 이메일인지 확인

## 로그인 (유저, 요리사) : /session
- Controller 
    - 로그인 성공
    - 로그인 실패
- Service    :
    - 로그인 처리.. (추후)
    - 이메일 비밀번호, 역할 모두 일치하는 경우 성공
    - 메일만 맞는 경우 실패
    - 모두 불일치 일 경우 실패
    
    (추후)
    - 이메일만 맞고 로그인 5번 실패할 경우 해당 계정 잠금
    - 5번 실패 시 아이디 잠금   
- Repository : 
    - Optional<Account> 이메일, 비밀번호, 일치하는 유저
    - Optional<Account> 이메일, 비밀번호, 일치하는 요리사
     
      
---
 
#package
- account : 계정 (회원가입, 로그인, 유저 내 정보, 요리사 내 정보)
    - Login
    - Info
- book : 예약 
    - book : 예약, 수락, 거절, 취소
- restaurant
- review
- rank

--- 
#Domain
- Entity : Account
    - username
    - password
    - email
    - roleType  : Role : USER, MANAGER
    - mail_yn : (optional) boolean
    - state : boolean

 