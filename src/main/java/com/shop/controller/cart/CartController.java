package com.shop.controller.cart;

import com.shop.dto.cart.CartRequestDto;
import com.shop.dto.cart.CartResponseDto;
import com.shop.security.JwtUtil;
import com.shop.service.cart.CartService;
import com.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    /**
     * 장바구니 추가 또는 수량 수정
     */
    @PostMapping
    public ResponseEntity<Long> addOrUpdate(@RequestHeader("Authorization") String authHeader,
                                            @RequestBody CartRequestDto dto) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        return ResponseEntity.ok(cartService.addOrUpdate(userId, dto));
    }

    /**
     * 로그인한 사용자 장바구니 조회
     */
    @GetMapping
    public ResponseEntity<List<CartResponseDto>> findByUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        return ResponseEntity.ok(cartService.findByUser(userId));
    }

    /**
     * 장바구니 항목 삭제
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartId) {
        cartService.delete(cartId);
        return ResponseEntity.noContent().build();
    }
}
