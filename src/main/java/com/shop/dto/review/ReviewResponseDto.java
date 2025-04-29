package com.shop.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private String username;
    private String productName;
    private String content;
    private int rating;
    private String createdAt;
}