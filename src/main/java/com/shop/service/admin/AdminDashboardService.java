package com.shop.service.admin;

import com.shop.dto.admin.CategoryRevenueDto;
import com.shop.dto.admin.DailyOrderDto;
import com.shop.dto.admin.DashboardResponseDto;
import com.shop.repository.order.OrderRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * 관리자 대시보드 통계 데이터 조회
     */
    public DashboardResponseDto getDashboardData() {
        int totalUsers = (int) userRepository.count();
        int totalOrders = (int) orderRepository.count();
        int totalRevenue = orderRepository.findTotalRevenue() != null
                ? orderRepository.findTotalRevenue()
                : 0;

        // 최근 7일간 일별 주문 수 조회
        List<DailyOrderDto> dailyOrders = orderRepository.countOrdersByDay(LocalDate.now().minusDays(6))
                .stream()
                .map(row -> new DailyOrderDto(
                        String.valueOf(row[0]),           // 날짜 (String 변환)
                        ((Number) row[1]).longValue()     // 주문 수
                ))
                .toList();

        // 카테고리별 매출
        List<CategoryRevenueDto> categoryRevenue = orderRepository.getRevenueByCategory()
                .stream()
                .map(row -> new CategoryRevenueDto(
                        String.valueOf(row[0]),           // 카테고리 이름
                        ((Number) row[1]).intValue()      // 매출
                ))
                .toList();

        return new DashboardResponseDto(
                totalUsers,
                totalOrders,
                totalRevenue,
                dailyOrders,
                categoryRevenue
        );
    }
}