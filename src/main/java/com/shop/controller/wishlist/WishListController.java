package com.shop.controller.wishlist;

import com.shop.dto.wishlist.WishListResponseDto;
import com.shop.entity.product.Product;
import com.shop.security.JwtUtil;
import com.shop.service.user.UserService;
import com.shop.service.wishlist.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;

    /**
     * 찜 목록 추가
     */
    @PostMapping
    public ResponseEntity<Long> addToWishList(@RequestHeader("Authorization") String authHeader,
                                              @RequestParam Long productId) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        Long wishListId = wishListService.addToWishList(userId, productId);
        return ResponseEntity.ok(wishListId);
    }

    /**
     * 찜 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<WishListResponseDto>> getMyWishList(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        return ResponseEntity.ok(wishListService.getWishList(userId));
    }

    /**
     * 찜 목록에서 상품 삭제
     */
    @DeleteMapping
    public ResponseEntity<Void> removeFromWishList(@RequestHeader("Authorization") String authHeader,
                                                   @RequestParam Long productId) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        wishListService.removeFromWishList(userId, productId);
        return ResponseEntity.noContent().build();
    }
}