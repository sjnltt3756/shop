package com.shop.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CouponResponseDto {
    private Long id;
    private String name;
    private int discountAmount;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean enabled;
}