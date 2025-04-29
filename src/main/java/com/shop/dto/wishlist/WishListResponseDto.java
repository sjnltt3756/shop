package com.shop.dto.wishlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WishListResponseDto {
    private Long productId;
    private String productName;
    private int price;
    private String imageUrl;
}