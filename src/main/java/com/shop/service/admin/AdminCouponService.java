package com.shop.service.admin;

import com.shop.dto.coupon.CouponRequestDto;
import com.shop.dto.coupon.CouponResponseDto;
import com.shop.entity.coupon.Coupon;
import com.shop.entity.coupon.UserCoupon;
import com.shop.entity.user.User;
import com.shop.exception.coupon.CouponNotFoundException;
import com.shop.repository.coupon.CouponRepository;
import com.shop.repository.coupon.UserCouponRepository;
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
    private final UserCouponRepository userCouponRepository;

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
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("존재하지 않는 쿠폰입니다"));

        List<User> users = userRepository.findAll();
        List<UserCoupon> issued = users.stream()
                .map(user -> UserCoupon.create(user, coupon))
                .collect(Collectors.toList());

        userCouponRepository.saveAll(issued);
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