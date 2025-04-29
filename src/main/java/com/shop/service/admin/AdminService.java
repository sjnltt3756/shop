package com.shop.service.admin;

import com.shop.dto.category.CategoryRequestDto;
import com.shop.dto.category.CategoryResponseDto;
import com.shop.dto.order.OrderResponseDto;
import com.shop.dto.product.ProductRequestDto;
import com.shop.dto.product.ProductResponseDto;
import com.shop.dto.review.ReviewResponseDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.category.Category;
import com.shop.entity.product.Product;
import com.shop.exception.category.CategoryNotFoundException;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.repository.category.CategoryRepository;
import com.shop.repository.order.OrderRepository;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.review.ReviewRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    // 👤 전체 회원 조회
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                )).toList();
    }

    // 👤 회원 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 📦 상품 등록
    @Transactional
    public Long createProduct(ProductRequestDto dto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));

        Product product = Product.create(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                dto.getImageUrl(),
                category
        );

        return productRepository.save(product).getId();
    }

    // 📦 상품 수정
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto, Long categoryId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));

        product.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                dto.getImageUrl(),
                category
        );

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory().getName()
        );
    }

    // 📦 상품 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getImageUrl(),
                        product.getCategory().getName()
                )).toList();
    }

    // 📦 상품 삭제
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
        productRepository.deleteById(id);
    }

    // 🗂️ 카테고리 등록
    public Long createCategory(CategoryRequestDto dto) {
        Category category = Category.create(dto.getName());
        return categoryRepository.save(category).getId();
    }

    // 🗂️ 카테고리 수정
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
        category.update(dto.getName());
        return new CategoryResponseDto(category.getId(), category.getName());
    }

    // 🗂️ 카테고리 삭제
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("존재하지 않는 카테고리입니다.");
        }
        categoryRepository.deleteById(id);
    }

    // 🛒 주문 조회
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getStatus(),
                        order.getTotalAmount(),
                        order.getOrderItems().stream()
                                .map(item -> new com.shop.dto.order.OrderItemResponseDto(
                                        item.getProduct().getName(),
                                        item.getQuantity(),
                                        item.getPrice()
                                )).toList(),
                        order.getUser().getId()
                )).toList();
    }

    // 🛒 주문 삭제
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    // ✍️ 리뷰 조회
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getUser().getUsername(),
                        review.getProduct().getName(),
                        review.getContent(),
                        review.getRating(),
                        review.getCreatedAt().toString()
                )).toList();
    }

    // ✍️ 리뷰 삭제
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}