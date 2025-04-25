package com.shop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemResponseDto {
    private final String productName;
    private final int quantity;
    private final int price;
}