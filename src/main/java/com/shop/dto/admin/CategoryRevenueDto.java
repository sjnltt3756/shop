package com.shop.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRevenueDto {
    private final String categoryName;
    private final int revenue;
}