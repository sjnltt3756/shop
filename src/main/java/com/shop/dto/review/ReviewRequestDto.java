package com.shop.dto.review;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long productId;
    private String content;
    private int rating;
}