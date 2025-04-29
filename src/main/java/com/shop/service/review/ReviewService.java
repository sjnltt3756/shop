package com.shop.service.review;

import com.shop.dto.review.ReviewRequestDto;
import com.shop.dto.review.ReviewResponseDto;
import com.shop.entity.product.Product;
import com.shop.entity.review.Review;
import com.shop.entity.user.User;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.exception.review.ReviewNotFoundException;
import com.shop.exception.user.UserNotFoundException;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.review.ReviewRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * 리뷰 등록
     */
    @Transactional
    public Long create(Long userId, ReviewRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("상품이 존재하지 않습니다."));

        Review review = Review.create(user, product, dto.getContent(), dto.getRating());
        return reviewRepository.save(review).getId();
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public void update(Long reviewId, Long userId, ReviewRequestDto dto) {
        Review review = getReviewByIdAndUser(reviewId, userId);
        review.update(dto.getContent(), dto.getRating());
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void delete(Long reviewId, Long userId) {
        Review review = getReviewByIdAndUser(reviewId, userId);
        reviewRepository.delete(review);
    }

    /**
     * 상품 리뷰 전체 조회
     */
    public List<ReviewResponseDto> findByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 본인 리뷰 조회
     */
    public List<ReviewResponseDto> findByUser(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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

    private Review getReviewByIdAndUser(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("리뷰가 존재하지 않습니다."));
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 리뷰만 수정/삭제할 수 있습니다.");
        }
        return review;
    }
}