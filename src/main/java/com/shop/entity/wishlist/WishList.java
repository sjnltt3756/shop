package com.shop.entity.wishlist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shop.entity.product.Product;
import com.shop.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference  // 순환 참조 허용
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference  // 순환 참조 허용
    private Product product;

    public static WishList create(User user, Product product) {
        WishList wishList = new WishList();
        wishList.user = user;
        wishList.product = product;
        return wishList;
    }
}