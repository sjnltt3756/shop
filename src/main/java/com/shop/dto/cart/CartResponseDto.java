package com.shop.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartResponseDto {
    private final Long id;
    private final String username;
    private final String productName;
    private final int quantity;
}
