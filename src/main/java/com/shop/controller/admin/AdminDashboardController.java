package com.shop.controller.admin;

import com.shop.dto.admin.DashboardResponseDto;
import com.shop.service.admin.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    /**
     * 관리자 대시보드 통계 정보 조회
     */
    @GetMapping
    public ResponseEntity<DashboardResponseDto> getDashboardData() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}