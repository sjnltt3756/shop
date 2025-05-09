package com.shop.service.admin;

import com.shop.dto.product.ProductRequestDto;
import com.shop.dto.product.ProductResponseDto;
import com.shop.entity.category.Category;
import com.shop.entity.product.Product;
import com.shop.exception.category.CategoryNotFoundException;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.repository.category.CategoryRepository;
import com.shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 전체 상품 조회
     */
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 상품 등록
     */
    public Long createProduct(ProductRequestDto dto, Long categoryId) {
        Category category = findCategory(categoryId);
        Product product = Product.create(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                dto.getImageUrl(),
                category
        );
        return productRepository.save(product).getId();
    }

    /**
     * 상품 수정
     */
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto, Long categoryId) {
        Product product = findProduct(id);
        Category category = findCategory(categoryId);

        product.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                dto.getImageUrl(),
                category
        );

        return toDto(product);
    }

    /**
     * 상품 삭제
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
        productRepository.deleteById(id);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
    }

    private ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory().getName()
        );
    }
}
