package com.shop.controller.admin;

import com.shop.dto.coupon.CouponRequestDto;
import com.shop.dto.coupon.CouponResponseDto;
import com.shop.service.admin.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    // 쿠폰 등록
    @PostMapping
    public ResponseEntity<Long> createCoupon(@RequestBody CouponRequestDto dto) {
        return ResponseEntity.ok(adminCouponService.createCoupon(dto));
    }

    // 전체 쿠폰 조회
    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> getAllCoupons() {
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }

    // 쿠폰 비활성화
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableCoupon(@PathVariable Long id) {
        adminCouponService.disableCoupon(id);
        return ResponseEntity.noContent().build();
    }

    // 전체 사용자에게 쿠폰 발급
    @PostMapping("/issue-to-all/{couponId}")
    public ResponseEntity<Void> issueCouponToAllUsers(@PathVariable Long couponId) {
        adminCouponService.issueCouponToAllUsers(couponId);
        return ResponseEntity.ok().build();
    }


}
