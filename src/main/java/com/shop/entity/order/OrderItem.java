package com.shop.entity.order;

import com.shop.entity.product.Product;
import com.shop.entity.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;  // 상품 수량
    private int price;     // 상품 가격

    private OrderItem(Order order, Product product, int quantity, int price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderItem create(Order order, Product product, int quantity, int price) {
        return new OrderItem(order, product, quantity, price);
    }
}