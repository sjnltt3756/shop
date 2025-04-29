package com.shop.controller.admin;

import com.shop.dto.review.ReviewResponseDto;
import com.shop.service.admin.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    // 전체 리뷰 조회
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(adminReviewService.getAllReviews());
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        adminReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}