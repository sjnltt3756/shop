package com.shop.repository.coupon;

import com.shop.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, Long> {
}