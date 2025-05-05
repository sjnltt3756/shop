# 🛒 쇼핑몰 프로젝트
이 프로젝트는 Spring Boot를 기반으로 한 쇼핑몰 프로젝트입니다.

## 주요 기능

- **사용자 관리**: Spring Security와 JWT 기반 토큰 인증을 통해 로그인/회원가입 기능을 제공합니다.
- **제품 관리**: 제품을 추가, 수정, 삭제할 수 있는 CRUD 기능을 제공합니다.
- **제품 검색**: 카테고리, 가격 범위 등을 기준으로 제품을 필터링하여 검색할 수 있는 기능을 제공합니다.
- **리뷰 기능**: 사용자가 구매한 제품에 대해 리뷰를 남길 수 있습니다.
- **쿠폰 시스템**: 사용자가 주문 시 쿠폰을 적용하여 할인 받을 수 있는 기능을 제공합니다. 쿠폰은 한 번만 사용할 수 있으며, 사용 내역은 사용자별로 추적됩니다.
- **관리자 대시보드**: 관리자는 사용자, 제품, 카테고리 관리 및 판매 추세, 주문 수 등의 통계를 확인할 수 있습니다.
- **주문 관리**: 사용자는 주문을 생성하고, 주문 내역을 확인하고, 주문 상태를 추적할 수 있습니다.
- **MySQL 데이터베이스**: 모든 데이터는 MySQL 데이터베이스에 저장되며, Spring Data JPA를 사용하여 ORM을 처리합니다.

## 사용 기술

- **백엔드**: Java, Spring Boot (3.4.4), Spring Data JPA, Spring Security
- **데이터베이스**: MySQL
- **보안**: JWT 인증
- **주요 의존성**:
    - Spring Boot Starter: `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `spring-boot-starter-web`, `spring-boot-starter-security`
    - JWT: `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
    - MySQL 커넥터: `mysql-connector-j`
    - Lombok을 사용하여 Java 코드 간소화

## 디렉토리 구조
├── build
│   ├── classes
│   │   └── java
│   │       └── main
│   │           └── com
│   │               └── shop
│   │                   ├── controller
│   │                   │   ├── admin
│   │                   │   │   ├── AdminCategoryController.class
│   │                   │   │   ├── AdminCouponController.class
│   │                   │   │   ├── AdminDashboardController.class
│   │                   │   │   ├── AdminOrderController.class
│   │                   │   │   ├── AdminProductController.class
│   │                   │   │   ├── AdminReviewController.class
│   │                   │   │   └── AdminUserController.class
│   │                   │   ├── cart
│   │                   │   │   └── CartController.class
│   │                   │   ├── category
│   │                   │   │   └── CategoryController.class
│   │                   │   ├── coupon
│   │                   │   │   └── UserCouponController.class
│   │                   │   ├── order
│   │                   │   │   └── OrderController.class
│   │                   │   ├── product
│   │                   │   │   └── ProductController.class
│   │                   │   ├── review
│   │                   │   │   └── ReviewController.class
│   │                   │   ├── user
│   │                   │   │   └── UserController.class
│   │                   │   └── wishlist
│   │                   │       └── WishListController.class
│   │                   ├── dto
│   │                   │   ├── admin
│   │                   │   │   ├── CategoryRevenueDto.class
│   │                   │   │   ├── DailyOrderDto.class
│   │                   │   │   └── DashboardResponseDto.class
│   │                   │   ├── cart
│   │                   │   │   ├── CartRequestDto.class
│   │                   │   │   └── CartResponseDto.class
│   │                   │   ├── category
│   │                   │   │   ├── CategoryRequestDto.class
│   │                   │   │   └── CategoryResponseDto.class
│   │                   │   ├── coupon
│   │                   │   │   ├── CouponRequestDto.class
│   │                   │   │   ├── CouponResponseDto.class
│   │                   │   │   └── UserCouponResponseDto.class
│   │                   │   ├── order
│   │                   │   │   ├── OrderItemRequestDto.class
│   │                   │   │   ├── OrderItemResponseDto.class
│   │                   │   │   ├── OrderRequestDto.class
│   │                   │   │   └── OrderResponseDto.class
│   │                   │   ├── product
│   │                   │   │   ├── ProductRequestDto.class
│   │                   │   │   └── ProductResponseDto.class
│   │                   │   ├── review
│   │                   │   │   ├── ReviewRequestDto.class
│   │                   │   │   └── ReviewResponseDto.class
│   │                   │   ├── user
│   │                   │   │   ├── LoginRequestDto.class
│   │                   │   │   ├── UserRequestDto.class
│   │                   │   │   └── UserResponseDto.class
│   │                   │   └── wishlist
│   │                   │       └── WishListResponseDto.class
│   │                   ├── entity
│   │                   │   ├── cart
│   │                   │   │   └── Cart.class
│   │                   │   ├── category
│   │                   │   │   └── Category.class
│   │                   │   ├── coupon
│   │                   │   │   ├── Coupon.class
│   │                   │   │   └── UserCoupon.class
│   │                   │   ├── order
│   │                   │   │   ├── Order.class
│   │                   │   │   ├── OrderItem.class
│   │                   │   │   └── OrderStatus.class
│   │                   │   ├── product
│   │                   │   │   └── Product.class
│   │                   │   ├── review
│   │                   │   │   └── Review.class
│   │                   │   ├── user
│   │                   │   │   ├── Role.class
│   │                   │   │   └── User.class
│   │                   │   └── wishlist
│   │                   │       └── WishList.class
│   │                   ├── exception
│   │                   │   ├── cart
│   │                   │   │   └── CartNotFoundException.class
│   │                   │   ├── category
│   │                   │   │   └── CategoryNotFoundException.class
│   │                   │   ├── coupon
│   │                   │   │   └── CouponNotFoundException.class
│   │                   │   ├── dto
│   │                   │   │   └── ErrorResponse.class
│   │                   │   ├── GlobalExceptionHandler.class
│   │                   │   ├── order
│   │                   │   │   ├── OrderAccessDeniedException.class
│   │                   │   │   └── OrderNotFoundException.class
│   │                   │   ├── product
│   │                   │   │   └── ProductNotFoundException.class
│   │                   │   ├── review
│   │                   │   │   └── ReviewNotFoundException.class
│   │                   │   ├── user
│   │                   │   │   └── UserNotFoundException.class
│   │                   │   └── wishlist
│   │                   │       └── WishListNotFoundException.class
│   │                   ├── repository
│   │                   │   ├── cart
│   │                   │   │   └── CartRepository.class
│   │                   │   ├── category
│   │                   │   │   └── CategoryRepository.class
│   │                   │   ├── coupon
│   │                   │   │   ├── CouponRepository.class
│   │                   │   │   └── UserCouponRepository.class
│   │                   │   ├── order
│   │                   │   │   └── OrderRepository.class
│   │                   │   ├── product
│   │                   │   │   └── ProductRepository.class
│   │                   │   ├── review
│   │                   │   │   └── ReviewRepository.class
│   │                   │   ├── user
│   │                   │   │   └── UserRepository.class
│   │                   │   └── wishlist
│   │                   │       └── WishListRepository.class
│   │                   ├── security
│   │                   │   ├── JwtAuthenticationFilter.class
│   │                   │   ├── JwtUtil.class
│   │                   │   └── SecurityConfig.class
│   │                   ├── service
│   │                   │   ├── admin
│   │                   │   │   ├── AdminCategoryService.class
│   │                   │   │   ├── AdminCouponService.class
│   │                   │   │   ├── AdminDashboardService.class
│   │                   │   │   ├── AdminOrderService.class
│   │                   │   │   ├── AdminProductService.class
│   │                   │   │   ├── AdminReviewService.class
│   │                   │   │   └── AdminUserService.class
│   │                   │   ├── cart
│   │                   │   │   └── CartService.class
│   │                   │   ├── category
│   │                   │   │   └── CategoryService.class
│   │                   │   ├── coupon
│   │                   │   │   └── UserCouponService.class
│   │                   │   ├── order
│   │                   │   │   └── OrderService.class
│   │                   │   ├── product
│   │                   │   │   ├── ProductService.class
│   │                   │   │   └── ProductSpecs.class
│   │                   │   ├── review
│   │                   │   │   └── ReviewService.class
│   │                   │   ├── user
│   │                   │   │   └── UserService.class
│   │                   │   └── wishlist
│   │                   │       └── WishListService.class
│   │                   └── ShopApplication.class