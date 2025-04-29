package com.shop.service.admin;

import com.shop.dto.review.ReviewResponseDto;
import com.shop.entity.review.Review;
import com.shop.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 전체 리뷰 조회
     */
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 리뷰 삭제
     */
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getUsername(),
                review.getProduct().getName(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}