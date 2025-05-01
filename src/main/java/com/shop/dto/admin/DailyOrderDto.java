package com.shop.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyOrderDto {
    private final String date;
    private final long orderCount;
}