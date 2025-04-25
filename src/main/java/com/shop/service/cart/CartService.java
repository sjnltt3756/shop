package com.shop.service.cart;

import com.shop.dto.cart.CartRequestDto;
import com.shop.dto.cart.CartResponseDto;
import com.shop.entity.cart.Cart;
import com.shop.entity.product.Product;
import com.shop.entity.user.User;
import com.shop.repository.cart.CartRepository;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 장바구니 추가 or 수량 업데이트
     */
    @Transactional
    public Long addOrUpdate(Long userId, CartRequestDto dto) {
        User user = findUser(userId);
        Product product = findProduct(dto.getProductId());

        Cart cart = cartRepository.findByUserIdAndProductId(userId, dto.getProductId())
                .map(c -> {
                    c.updateQuantity(dto.getQuantity());
                    return c;
                })
                .orElseGet(() -> Cart.create(user, product, dto.getQuantity()));

        return cartRepository.save(cart).getId();
    }

    /**
     * 장바구니 조회
     */
    public List<CartResponseDto> findByUser(Long userId) {
        return cartRepository.findByUserId(userId).stream()
                .map(cart -> new CartResponseDto(
                        cart.getId(),
                        cart.getUser().getUsername(),
                        cart.getProduct().getName(),
                        cart.getQuantity()
                )).toList();
    }

    /**
     * 장바구니 항목 삭제
     */
    public void delete(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }
}
