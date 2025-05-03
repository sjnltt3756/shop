package com.shop.entity.user;

import com.shop.entity.cart.Cart;
import com.shop.entity.coupon.Coupon;
import com.shop.entity.coupon.UserCoupon;
import com.shop.entity.order.Order;
import com.shop.entity.review.Review;
import com.shop.entity.wishlist.WishList;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 기본값 제거 → 생성자/팩토리에서 직접 설정

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCoupon> userCoupons = new ArrayList<>();
    /**
     * 기본 생성자 (정적 팩토리 메서드에서만 호출)
     */
    private User(String username, String password, String name, String email, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * 일반 사용자 생성
     */
    public static User create(String username, String password, String name, String email) {
        return new User(username, password, name, email, Role.USER);
    }

    /**
     * 관리자 사용자 생성
     */
    public static User createAdmin(String username, String password, String name, String email) {
        return new User(username, password, name, email, Role.ADMIN);
    }

    /**
     * 사용자 정보 수정
     */
    public void updateInfo(String password, String name, String email, String username) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.username = username;
    }
}