package com.shop.entity.user;

import com.shop.entity.cart.Cart;
import com.shop.entity.order.Order;
import com.shop.entity.order.OrderItem;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // 생성자 (필수 필드 중심)
    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // 정적 팩토리 메서드
    public static User create(String username, String password, String name, String email) {
        return new User(username, password, name, email);
    }
    // 유저 정보 수정
    public void updateInfo(String password, String name, String email, String username) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.username = username;
    }
}
