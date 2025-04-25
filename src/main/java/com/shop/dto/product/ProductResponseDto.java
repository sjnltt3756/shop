package com.shop.dto.product;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final int price;
    private final int stockQuantity;
    private final String imageUrl;
    private final String categoryName;
}
