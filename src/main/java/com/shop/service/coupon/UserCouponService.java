package com.shop.service.coupon;

import com.shop.dto.coupon.CouponResponseDto;
import com.shop.dto.coupon.UserCouponResponseDto;
import com.shop.entity.coupon.Coupon;
import com.shop.entity.coupon.UserCoupon;
import com.shop.entity.user.User;
import com.shop.exception.coupon.CouponNotFoundException;
import com.shop.repository.coupon.CouponRepository;
import com.shop.repository.coupon.UserCouponRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public List<UserCouponResponseDto> getMyCoupons(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

        return userCouponRepository.findByUser(user).stream()
                .map(userCoupon -> {
                    Coupon coupon = userCoupon.getCoupon();
                    boolean isUsable = coupon.isEnabled() && !userCoupon.isUsed();
                    return new UserCouponResponseDto(
                            userCoupon.getId(), // ✅ 여기로 변경
                            coupon.getName(),
                            coupon.getDiscountAmount(),
                            coupon.getStartAt(),
                            coupon.getEndAt(),
                            userCoupon.isUsed()
                    );
                })
                .collect(Collectors.toList());
    }

    public void useCoupon(Long userCouponId, Long userId) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new CouponNotFoundException("쿠폰 없음"));

        if (!userCoupon.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("다른 유저의 쿠폰입니다.");
        }

        userCoupon.use();
        userCouponRepository.save(userCoupon);
    }
}
