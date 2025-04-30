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
     * 상품 검색 / 필터링
     * @param name
     * @param categoryId
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId
    ) {
        return ResponseEntity.ok(productService.search(name, categoryId));
    }
}