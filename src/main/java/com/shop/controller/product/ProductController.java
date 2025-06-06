package com.shop.controller.product;

import com.shop.dto.product.ProductResponseDto;
import com.shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 전체 상품 조회
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * 단일 상품 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }


    /**
     * 상품 검색 / 카테고리, 금액 필터링
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(productService.searchProducts(name, categoryId, minPrice, maxPrice, sort));
    }

    /**
     * 인기 상품 조회 (찜 순)
     */
    @GetMapping("/popular/wishlist")
    public ResponseEntity<List<ProductResponseDto>> getPopularByWishList() {
        return ResponseEntity.ok(productService.getPopularByWishList());
    }

    /**
     * 인기 상품 조회 (주문 많은 순)
     */
    @GetMapping("/popular/order")
    public ResponseEntity<List<ProductResponseDto>> getPopularByOrder() {
        return ResponseEntity.ok(productService.getPopularByOrder());
    }
}