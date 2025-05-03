package com.shop.entity.coupon;

import com.shop.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    private boolean used = false;

    private LocalDateTime issuedAt;

    public static UserCoupon create(User user, Coupon coupon) {
        UserCoupon uc = new UserCoupon();
        uc.user = user;
        uc.coupon = coupon;
        uc.issuedAt = LocalDateTime.now();
        return uc;
    }

    public void use() {
        this.used = true;
    }
}
