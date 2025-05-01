package com.shop.repository.product;

import com.shop.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p LEFT JOIN p.wishLists w GROUP BY p.id ORDER BY COUNT(w.id) DESC")
    List<Product> findPopularByWishList();

    @Query("SELECT p FROM Product p LEFT JOIN OrderItem oi ON oi.product = p " +
            "GROUP BY p.id ORDER BY COUNT(oi.id) DESC")
    List<Product> findPopularByOrder();
}
