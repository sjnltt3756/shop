package com.shop.service.product;

import com.shop.dto.product.ProductResponseDto;
import com.shop.entity.product.Product;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 전체 상품 조회
     */
    public List<ProductResponseDto> findAll() {
        return convertToDtoList(productRepository.findAll());
    }

    /**
     * 단일 상품 조회
     */
    public ProductResponseDto findById(Long id) {
        return toDto(findProductById(id));
    }

    /**
     * 상품 검색 및 필터링 (이름, 카테고리, 가격범위, 정렬 포함)
     */
    public List<ProductResponseDto> searchProducts(String name, Long categoryId, Integer minPrice, Integer maxPrice, String sort) {
        Sort sortOption = resolveSortOption(sort);
        List<Product> products = productRepository.findAll(
                ProductSpecs.filter(name, categoryId, minPrice, maxPrice),
                sortOption
        );
        return convertToDtoList(products);
    }

    /**
     * 인기 상품 조회(찜 순)
     */
    public List<ProductResponseDto> getPopularByWishList() {
        List<Product> products = productRepository.findPopularByWishList();
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 인기 상품 조회(주문 많은 순)
     */
    public List<ProductResponseDto> getPopularByOrder() {
        List<Product> products = productRepository.findPopularByOrder();
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 상품 ID로 조회 (엔티티)
     */
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다. ID: " + id));
    }

    /**
     * 정렬 옵션 처리
     */
    private Sort resolveSortOption(String sort) {
        if (sort == null || sort.isBlank()) return Sort.unsorted();

        return switch (sort) {
            case "priceAsc" -> Sort.by("price").ascending();
            case "priceDesc" -> Sort.by("price").descending();
            case "latest" -> Sort.by("id").descending(); // createdAt 컬럼 사용 시 교체
            default -> Sort.unsorted();
        };
    }

    /**
     * Product 엔티티 → DTO 변환
     */
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

    /**
     * Product 리스트 → DTO 리스트 변환
     */
    private List<ProductResponseDto> convertToDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }
}