package com.shop.repository.product;

import com.shop.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // 이름과 카테고리 동적 필터링
    @Query("SELECT p FROM Product p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    List<Product> searchProducts(@Param("name") String name, @Param("categoryId") Long categoryId);
}
