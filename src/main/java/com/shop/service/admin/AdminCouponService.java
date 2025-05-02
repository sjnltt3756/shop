package com.shop.service.admin;

import com.shop.dto.coupon.CouponRequestDto;
import com.shop.dto.coupon.CouponResponseDto;
import com.shop.entity.coupon.Coupon;
import com.shop.entity.user.User;
import com.shop.exception.coupon.CouponNotFoundException;
import com.shop.repository.coupon.CouponRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    /**
     * 쿠폰 생성
     */
    public Long createCoupon(CouponRequestDto dto) {
        Coupon coupon = Coupon.create(dto.getName(), dto.getDiscountAmount(), dto.getStartAt(), dto.getEndAt());
        return couponRepository.save(coupon).getId();
    }

    /**
     * 전체 쿠폰 목록 조회
     */
    public List<CouponResponseDto> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 쿠폰 비활성화
     */
    public void disableCoupon(Long id) {
        Coupon coupon = findCouponById(id);
        coupon.disable();
        couponRepository.save(coupon);  // 상태 변경 후 저장
    }

    /**
     * ID로 쿠폰 조회
     */
    private Coupon findCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("존재하지 않는 쿠폰입니다."));
    }

    public void issueCouponToAllUsers(Long couponId) {
        // 쿠폰 찾기
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        // 모든 사용자에게 쿠폰 발급
        List<User> users = userRepository.findAll();
        for (User user : users) {
            coupon.assignUser(user);  // 쿠폰 발급
        }
        couponRepository.saveAll(users.stream().map(u -> coupon).collect(Collectors.toList()));
    }

    /**
     * Coupon Entity -> CouponResponseDto 변환
     */
    private CouponResponseDto toDto(Coupon coupon) {
        return new CouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getStartAt(),
                coupon.getEndAt(),
                coupon.isEnabled()
        );
    }
}