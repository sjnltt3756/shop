package com.shop.service.admin;

import com.shop.dto.order.OrderResponseDto;
import com.shop.dto.product.ProductRequestDto;
import com.shop.dto.product.ProductResponseDto;
import com.shop.dto.review.ReviewResponseDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.cart.Cart;
import com.shop.entity.category.Category;
import com.shop.entity.order.Order;
import com.shop.entity.product.Product;
import com.shop.entity.review.Review;
import com.shop.entity.user.User;
import com.shop.entity.wishlist.WishList;
import com.shop.repository.cart.CartRepository;
import com.shop.repository.category.CategoryRepository;
import com.shop.repository.order.OrderRepository;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.review.ReviewRepository;
import com.shop.repository.user.UserRepository;
import com.shop.repository.wishlist.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final WishListRepository wishListRepository;
    private final CartRepository cartRepository;

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

    // 📦 전체 상품 조회
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
        productRepository.deleteById(id);
    }

    // 🗂️ 전체 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 🗂️ 카테고리 삭제
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // 🛒 전체 주문 조회
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

    // ✍️ 리뷰 전체 조회
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
