package com.shop.entity.order;

import com.shop.entity.cart.Cart;
import com.shop.entity.product.Product;
import com.shop.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private String status; // 주문 상태 (예: 주문 완료, 배송 중, 완료 등)

    private int totalAmount;  // 총 금액

    private Order(User user, List<OrderItem> orderItems, String status, int totalAmount) {
        this.user = user;
        this.orderItems = orderItems;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public static Order create(User user, List<OrderItem> orderItems, String status, int totalAmount) {
        return new Order(user, orderItems, status, totalAmount);
    }
}