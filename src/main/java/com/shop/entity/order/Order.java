package com.shop.entity.order;

import com.shop.entity.address.Address;
import com.shop.entity.coupon.UserCoupon;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_coupon_id")
    private UserCoupon userCoupon; // 🔥 쿠폰 연관관계 추가

    private int totalPrice;      // 할인 전
    private int finalPrice;      // 할인 후

    private String status;

    private int totalAmount;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 날짜 필드 추가

    private Order(User user, List<OrderItem> orderItems, String status, int totalAmount, int totalPrice, int finalPrice, UserCoupon userCoupon, Address address) {
        this.user = user;
        this.orderItems = orderItems;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(this);
        }
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        this.finalPrice = finalPrice;
        this.userCoupon = userCoupon;
        this.address = address;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public static Order create(User user, List<OrderItem> orderItems, String status, int totalAmount, int totalPrice, int finalPrice, UserCoupon userCoupon, Address address) {
        return new Order(user,orderItems,status,totalAmount,totalPrice,finalPrice,userCoupon,address);
    }

    public void applyDiscount(int discountAmount) {
        this.finalPrice = Math.max(0, this.totalPrice - discountAmount);
    }
}