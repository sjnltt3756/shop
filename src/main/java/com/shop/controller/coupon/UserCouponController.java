package com.shop.controller.coupon;

import com.shop.dto.coupon.CouponResponseDto;
import com.shop.dto.coupon.UserCouponResponseDto;
import com.shop.security.JwtUtil;
import com.shop.service.coupon.UserCouponService;
import com.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/user/coupons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserCouponResponseDto>> getMyCoupons(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");  // Bearer 제외하고 토큰만 추출
        String username = JwtUtil.extractUsername(token);   // JwtUtil로 username 추출
        Long userId = userService.findIdByUsername(username); // username으로 userId 찾기

        List<UserCouponResponseDto> coupons = userCouponService.getMyCoupons(userId);
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/{userCouponId}/use")
    public ResponseEntity<Void> useCoupon(
            @PathVariable Long userCouponId,
            @RequestHeader("Authorization") String authHeader  // Authorization header를 통해 token 받기
    ) {
        String token = authHeader.replace("Bearer ", "");  // Bearer 제외하고 토큰만 추출
        String username = JwtUtil.extractUsername(token);   // JwtUtil로 username 추출
        Long userId = userService.findIdByUsername(username); // username으로 userId 찾기

        userCouponService.useCoupon(userCouponId, userId); // userCouponService에서 userId 사용
        return ResponseEntity.ok().build();
    }
}