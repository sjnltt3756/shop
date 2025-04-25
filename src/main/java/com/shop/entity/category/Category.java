package com.shop.entity.category;

import com.shop.entity.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;  // 카테고리별로 관련된 상품들

    // 생성자
    public Category(String name) {
        this.name = name;
    }

    // 정적 팩토리 메서드
    public static Category create(String name) {
        return new Category(name);
    }
    // 카테고리 이름 수정
    public void update(String name) {
        this.name = name;
    }
}
