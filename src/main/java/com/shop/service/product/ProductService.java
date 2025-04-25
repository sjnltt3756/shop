package com.shop.service.product;

import com.shop.dto.product.ProductRequestDto;
import com.shop.dto.product.ProductResponseDto;
import com.shop.entity.product.Product;
import com.shop.entity.category.Category;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // 상품 등록
    public Long create(ProductRequestDto dto, Long categoryId) {
        // 카테고리 찾기
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Product product = Product.create(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                dto.getImageUrl(),
                category // 카테고리 연관
        );
        return productRepository.save(product).getId();
    }

    // 전체 조회
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // 단건 조회
    public ProductResponseDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return toDto(product);
    }

    // 수정
    @Transactional
    public ProductResponseDto update(Long id, ProductRequestDto dto, Long categoryId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 카테고리 업데이트
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        product.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                dto.getImageUrl(),
                category // 카테고리 연관
        );

        return toDto(product);
    }

    // 상품 삭제
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        productRepository.deleteById(id);
    }

    // 상품 정보를 DTO로 변환
    private ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory().getName() // 카테고리 이름 포함
        );
    }
}