package com.shop.controller.review;

import com.shop.dto.review.ReviewRequestDto;
import com.shop.dto.review.ReviewResponseDto;
import com.shop.security.JwtUtil;
import com.shop.service.review.ReviewService;
import com.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * 리뷰 작성
     */
    @PostMapping
    public ResponseEntity<Long> create(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody ReviewRequestDto dto) {
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(reviewService.create(userId, dto));
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id,
                                       @RequestBody ReviewRequestDto dto) {
        Long userId = extractUserId(authHeader);
        reviewService.update(id, userId, dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id) {
        Long userId = extractUserId(authHeader);
        reviewService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 상품 리뷰 조회
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.findByProduct(productId));
    }

    /**
     * 본인 리뷰 조회
     */
    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponseDto>> findByUser(@RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(reviewService.findByUser(userId));
    }

    private Long extractUserId(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractRole(token);
        return userService.findIdByUsername(username);
    }
}