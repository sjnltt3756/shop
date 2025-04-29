package com.shop.service.category;

import com.shop.dto.category.CategoryResponseDto;
import com.shop.entity.category.Category;
import com.shop.exception.category.CategoryNotFoundException;
import com.shop.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 전체 카테고리 조회
     */
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResponseDto(category.getId(), category.getName()))
                .toList();
    }

    /**
     * 단일 카테고리 조회
     */
    public CategoryResponseDto findById(Long id) {
        Category category = findCategory(id);
        return new CategoryResponseDto(category.getId(), category.getName());
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
    }
}
