package com.shop.service.category;

import com.shop.dto.category.CategoryRequestDto;
import com.shop.dto.category.CategoryResponseDto;
import com.shop.entity.category.Category;
import com.shop.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 등록
    public Long create(CategoryRequestDto dto) {
        Category category = Category.create(dto.getName());
        return categoryRepository.save(category).getId();
    }

    // 전체 카테고리 조회
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResponseDto(category.getId(), category.getName()))
                .toList();
    }

    // 단일 카테고리 조회
    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return new CategoryResponseDto(category.getId(), category.getName());
    }

    // 카테고리 수정
    @Transactional
    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.update(dto.getName());

        return new CategoryResponseDto(category.getId(), category.getName());
    }

    // 카테고리 삭제
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }
        categoryRepository.deleteById(id);
    }
}