package com.shop.entity.product;

import com.shop.entity.cart.Cart;
import com.shop.entity.category.Category;
import com.shop.entity.order.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")  // 외래 키를 `category_id`로 설정
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 정적 팩토리 메서드
    public static Product create(String name, String description, int price, int stockQuantity, String imageUrl, Category category) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.stockQuantity = stockQuantity;
        product.imageUrl = imageUrl;
        product.category = category;
        return product;
    }

    public void update(String name, String description, int price, int stockQuantity, String imageUrl, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}