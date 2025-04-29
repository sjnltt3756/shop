package com.shop.controller.admin;

import com.shop.dto.product.ProductRequestDto;
import com.shop.dto.product.ProductResponseDto;
import com.shop.service.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminProductService adminProductService;

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(adminProductService.getAllProducts());
    }

    // 상품 등록
    // 카테고리id는 쿼리파라미터로 요청
    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequestDto dto, @RequestParam Long categoryId) {
        return ResponseEntity.ok(adminProductService.createProduct(dto, categoryId));
    }

    // 상품 수정
    // 카테고리id는 쿼리파라미터로 요청
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductRequestDto dto,
                                                            @RequestParam Long categoryId) {
        return ResponseEntity.ok(adminProductService.updateProduct(id, dto, categoryId));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}