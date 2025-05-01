package com.shop.entity.order;

import com.shop.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    private String status;
    private int totalAmount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 날짜 필드 추가

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