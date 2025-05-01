package com.shop.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardResponseDto {
    private final int totalUsers;
    private final int totalOrders;
    private final int totalRevenue;
    private final List<DailyOrderDto> dailyOrders;
    private final List<CategoryRevenueDto> categoryRevenue;
}