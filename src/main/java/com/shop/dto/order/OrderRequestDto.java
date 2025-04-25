package com.shop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderRequestDto {
    private final Long userId;
    private final List<OrderItemRequestDto> orderItems; // 주문 항목들
    private final String status; // 주문 상태
}