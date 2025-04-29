package com.shop.controller.admin;

import com.shop.dto.category.CategoryRequestDto;
import com.shop.dto.category.CategoryResponseDto;
import com.shop.dto.order.OrderResponseDto;
import com.shop.dto.product.ProductRequestDto;
import com.shop.dto.product.ProductResponseDto;
import com.shop.dto.review.ReviewResponseDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.category.Category;
import com.shop.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // 👤 전체 회원 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // 👤 회원 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 📦 상품 등록
    @PostMapping("/products")
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequestDto dto, @RequestParam Long categoryId) {
        Long productId = adminService.createProduct(dto, categoryId);
        return ResponseEntity.ok(productId);
    }

    // 📦 상품 수정
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto dto, @RequestParam Long categoryId) {
        ProductResponseDto updatedProduct = adminService.updateProduct(id, dto, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }

    // 📦 상품 삭제
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 🗂️ 카테고리 등록
    @PostMapping("/categories")
    public ResponseEntity<Long> createCategory(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(adminService.createCategory(dto));
    }

    // 🗂️ 카테고리 수정
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(adminService.updateCategory(id, dto));
    }

    // 🗂️ 카테고리 삭제
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // 🛒 전체 주문 조회
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    // 🛒 주문 삭제
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        adminService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // ✍️ 리뷰 전체 조회
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(adminService.getAllReviews());
    }

    // ✍️ 리뷰 삭제
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        adminService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}