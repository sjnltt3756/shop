package com.shop.service.wishlist;

import com.shop.dto.wishlist.WishListResponseDto;
import com.shop.entity.wishlist.WishList;
import com.shop.entity.product.Product;
import com.shop.entity.user.User;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.exception.user.UserNotFoundException;
import com.shop.repository.wishlist.WishListRepository;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 찜 목록 추가
     */
    @Transactional
    public Long addToWishList(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

        // 중복 찜 처리 방지
        wishListRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(w -> {
                    throw new IllegalArgumentException("이미 찜한 상품입니다.");
                });

        WishList wishList = WishList.create(user, product);
        return wishListRepository.save(wishList).getId();
    }

    /**
     * 찜 목록 조회
     */
    public List<WishListResponseDto> getWishList(Long userId) {
        return wishListRepository.findByUserId(userId).stream()
                .map(w -> {
                    Product p = w.getProduct();
                    return new WishListResponseDto(
                            p.getId(),
                            p.getName(),
                            p.getPrice(),
                            p.getImageUrl()
                    );
                })
                .toList();
    }

    /**
     * 찜 목록 삭제
     */
    public void removeFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("찜 목록에 없는 상품입니다."));

        wishListRepository.delete(wishList);
    }
}