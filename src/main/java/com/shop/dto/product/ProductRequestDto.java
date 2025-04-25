package com.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProductRequestDto {
    private final String name;
    private final String description;
    private final int price;
    private final int stockQuantity;
    private final String imageUrl;
}
