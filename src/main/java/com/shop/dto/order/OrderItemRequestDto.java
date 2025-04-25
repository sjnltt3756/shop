package com.shop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderItemRequestDto {
    private final Long productId;
    private final int quantity;
    private final int price;
}