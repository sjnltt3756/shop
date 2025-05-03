package com.shop.repository.coupon;

import com.shop.entity.coupon.UserCoupon;
import com.shop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser(User user);
}
