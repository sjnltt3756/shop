package com.shop.entity.coupon;

import com.shop.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;           // 쿠폰 이름
    private int discountAmount;    // 할인 금액
    private LocalDateTime startAt; // 사용 시작일
    private LocalDateTime endAt;   // 사용 종료일
    private boolean enabled;       // 활성화 여부


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;            // 발급된 사용자

    public static Coupon create(String name, int discountAmount, LocalDateTime startAt, LocalDateTime endAt) {
        Coupon coupon = new Coupon();
        coupon.name = name;
        coupon.discountAmount = discountAmount;
        coupon.startAt = startAt;
        coupon.endAt = endAt;
        coupon.enabled = true;  // 기본적으로 활성화된 쿠폰
        return coupon;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    public void disable() {
        this.enabled = false;
    }
}