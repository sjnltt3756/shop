package com.shop.controller.category;

import com.shop.dto.category.CategoryResponseDto;
import com.shop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    /**
     * 전체 카테고리 조회
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * 단일 카테고리 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}