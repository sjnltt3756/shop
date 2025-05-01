package com.shop.repository.order;

import com.shop.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);

    // 총 매출
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Integer findTotalRevenue();

    // 일자별 주문 수
    @Query(value = """
    SELECT DATE(o.created_at) AS date, COUNT(o.id) AS order_count
    FROM orders o
    WHERE o.created_at >= :startDate
    GROUP BY DATE(o.created_at)
    ORDER BY DATE(o.created_at)
    """, nativeQuery = true)
    List<Object[]> countOrdersByDay(@Param("startDate") LocalDate startDate);

    // 카테고리별 매출
    @Query(value = """
    SELECT c.name, SUM(oi.price * oi.quantity)
    FROM orders o
    JOIN order_item oi ON o.id = oi.order_id
    JOIN product p ON p.id = oi.product_id
    JOIN category c ON c.id = p.category_id
    GROUP BY c.name
""", nativeQuery = true)
    List<Object[]> getRevenueByCategory();
}
