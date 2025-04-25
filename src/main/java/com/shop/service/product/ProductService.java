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

    public Long create(ProductRequestDto dto, Long categoryId) {
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

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public ProductResponseDto findById(Long id) {
        return toDto(findProduct(id));
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductRequestDto dto, Long categoryId) {
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

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        productRepository.deleteById(id);
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

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }
}