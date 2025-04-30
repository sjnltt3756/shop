package com.shop.service.product;

import com.shop.dto.product.ProductResponseDto;
import com.shop.entity.product.Product;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 조회
     */
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 단건 상품 조회
     */
    public ProductResponseDto findById(Long id) {
        return toDto(findProduct(id));
    }

    /**
     * 상품 검색 / 카테고리 필터링
     */
    public List<ProductResponseDto> search(String name, Long categoryId) {
        return productRepository.searchProducts(name, categoryId).stream()
                .map(this::toDto)
                .toList();
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
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }
}
