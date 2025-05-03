package com.shop.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserCouponResponseDto {

    private Long id;
    private String name;
    private int discountAmount;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean isUsed;  // 유저 사용여부 (사용하면 True)
}
