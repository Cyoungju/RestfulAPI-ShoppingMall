
# 🛍shoppingMall - Restful API
Restful API를 활용한 쇼핑몰 만들기<br>
<a href="https://github.com/Cyoungju/RestfulAPI-ShoppingMall/blob/main/Image/erd.png" target="_blank">쇼핑몰 ERD</a>


## 개발환경
- IDE: IntelliJ IDEA Community
- Gradle - Groovy, Java 17
- Jar 11
- Spring Boot 2.7.6
- jvm.convert 3.3.2
- JDK 11
- mysql 8.0.35
- Lombok
- Spring Web
- Spring Data JPA
- Thymeleaf
<br><br>

## 쇼핑몰 주요 기능
### 1. 제품관리
- 쇼핑몰에서 판매되는 제품을 효과적으로 관리하고 제공하기
- 제품 등록, 수정, 삭제
- 제품 목록 조회, 단일 제품 조회

  #### a. 제품 등록 (POST "/products")
  - 새로운 제품 등록
  - 요청 본문  
    - 제품 정보 (JSON 형식)
  - 응답
    - 201 Created: 제품 등록 성공
    - 400 Bad Request: 잘못된 요청 형식<br><br>

  <img src="./Image/productSave.jpg"><br>

  #### b. 제품 조회 (GET "/products")
  - 제품 목록 조회
  - 응답
    - 200 OK: 성공적으로 제품 목록을 반환
    - 404 Not Found: 해당하는 제품이 없을 경우 <br><br>

  <img src="./Image/productFindAll.jpg"><br>

  #### c. 단일 제품 조회 (GET "/products/{id}")
  - {id}를 이용해서 단일 상품 조회
  - 응답
    - 200 OK: 성공적으로 제품 정보를 반환
    - 404 Not Found: 해당 제품이 없을 경우<br><br>

  <img src="./Image/productFindById.jpg">

  #### d. 제품 수정 (PUT "/products/{id}")
  - {id}를 이용해서 해당 제품 정보 수정
  - 요청 본문
    - 수정된 제품 정보 (JSON 형식)
  - 응답
    - 200 OK: 성공적으로 수정된 제품 정보를 반환
    - 404 Not Found: 해당 제품이 없을 경우
    - 400 Bad Request: 잘못된 요청 형식<br><br>

  <img src="./Image/productUpdate.jpg">

  #### e. 제품 삭제 (DELETE "/products/{id}")
  - {id}를 이용해 특정 제품 삭제
  - 응답
    - 204 No Content: 성공적으로 제품 삭제
    - 404 Not Found: 해당 제품이 없을 경우<br><br>

  <img src="./Image/productDelete.jpg">



### 2. 제품 옵션 관리
- 제품에 대한 다양한 선택 사항
- 생성, 조회, 수정 및 삭제

  #### a. 옵션 저장(POST "/options")
  - 새로운 옵션을 생성하고 저장
  - 요청 본문 : 옵션 정보 (JSON 형식)<br><br>

  <img src="./Image/optionSave.jpg">

  #### b. 옵션조회 (GET "/products/{id}/options")
  - 특정 상품에 대한 모든 옵션을 조회<br><br>

  <img src="./Image/optionFindAll.jpg">

  #### c. 전체 옵션 조회(GET "/options")
  - 모든 상품의 옵션을 조회<br><br>

  <img src="./Image/optionfindByProductId.jpg">

  #### d. 옵션 수정(PUT "/options/{id}")
  - 기존 옵션 수정
  - 요청 본문 : 수정돈 옵션의 정보 (JSON 형식)<br><br>

  <img src="./Image/optionUpdate.jpg">

  #### e. 옵션 삭제 (DELETE "/products/{id}/options/{optionId}")
  - 특정 상품의 특정 옵션 삭제<br><br>

  <img src="./Image/optionDelete.jpg">

### 3. 카트 관리
- JWT 인증을 받은 사용자만 접근 가능
- 등록되어있는 상품을 카트에서 추가 삭제 조회 수정
  #### a. 카트 등록(POST "/carts")
  <img src="./Image/cartSave.jpg">

  #### b. 카트 수정(PUT "/carts")
  <img src="./Image/cartUpdate.jpg">

  #### c. 카트 전체 상품 확인(GET "/carts")
  <img src="./Image/cartList.jpg">

  #### d. 카트 삭제(DELETE "/carts")
  <img src="./Image/cartDelete.jpg">


### 4. 주문관리
- JWT 인증을 받은 사용자만 접근 가능
- 주문 관리 기능

  #### a. 주문 상품 저장(POST "/orders")
  - item 생성후 주문 저장<br><br>

  <img src="./Image/orderSave.jpg">

  #### b. 주문 결과 확인(GET "/orders/{id}")
  - 하나의 목록 조회<br><br>

  <img src="./Image/orderFindById.jpg">

  #### c. 주문 삭제(DELETE "/orders")
  <img src="./Image/orderDelete.jpg">


## 사용자 관리
- JWT 인증 : 사용자는 JWT 토큰을 헤더에 포함하여 요청해야 함

### 1. 회원가입 (/join)
<img src="./Image/join.jpg">

### 2. 로그인 (/login)
<img src="./Image/login.jpg">


## 향후 업데이트 예정 기능
### 1. kakao Rest Api 로그인 기능 추가


## 버전

### v2.3.3 (2023.12.10)
- [수정] Restful api Endpoint 수정  

### v2.3.2 (2023.12.08)
- [수정] 코드 수정및 리팩토링
- [수정] 메인페이지 수정(/)

### v2.3.1 (2023.12.07)
- [추가] 카트 삭제(DELETE "/carts")
- [추가] 주문 상품 저장(POST "/orders")
- [추가] 주문 결과 확인(GET "/orders/{id}")
- [추가] 주문 삭제(DELETE "/orders")
- [수정] 코드 수정및 리팩토링
    - product save 엔티티-> DTO로 수정
    - UserController -> USerService분리
- [추가] 사용자 관리 - 로그인 (/join)

### v2.2.0 (2023.12.06)
- [추가] 옵션 수정(PUT /options/{id})
- [추가] 카트 등록(POST "/carts")
- [추가] 카트 수정(PUT "/carts")
- [추가] 카트 전체 상품 확인(GET "/carts")


### v2.1.0 (2023.12.05)
- [추가] 옵션 조회(GET /options)
- [추가] 옵션 등록(POST /options)
- [추가] 옵션 삭제(DELETE /options)
- [추가] 사용자 관리 - 회원가입 (/join)
- [추가] 시큐리티, 예외처리 추가


### v1.0.0 (2023.12.04)
- [추가] 상품 등록(POST /products)
- [추가] 상품 수정(PUT /products)
- [추가] 상품 전체 조회(GET /products/{id})
- [추가] 상품 조회(GET /products)
- [추가] 상품 삭2(DELETE /products)
