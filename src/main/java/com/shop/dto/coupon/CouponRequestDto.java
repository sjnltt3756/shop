package com.shop.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequestDto {
    private String name;
    private int discountAmount;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}