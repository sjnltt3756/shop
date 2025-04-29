package com.shop.controller.admin;

import com.shop.dto.category.CategoryRequestDto;
import com.shop.dto.category.CategoryResponseDto;
import com.shop.service.admin.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    // 전체 카테고리 조회
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(adminCategoryService.getAllCategories());
    }

    // 카테고리 등록
    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(adminCategoryService.createCategory(dto));
    }

    // 카테고리 수정
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id,
                                                              @RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(adminCategoryService.updateCategory(id, dto));
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}