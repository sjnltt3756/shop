package com.shop.service.admin;

import com.shop.dto.order.OrderItemResponseDto;
import com.shop.dto.order.OrderResponseDto;
import com.shop.entity.order.Order;
import com.shop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    /**
     * 전체 주문 조회
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            System.out.println("조회된 주문 수: " + orders.size());
            return orders.stream()
                    .map(this::toDto)
                    .toList();
        } catch (Exception e) {
            System.out.println("getAllOrders 예외 발생");
            e.printStackTrace(); // 콘솔에 스택트레이스 출력
            throw e; // 예외 다시 던지기 (원하는 경우)
        }
    }

    /**
     * 주문 삭제
     */
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    /**
     * Order → OrderResponseDto 변환
     */
    private OrderResponseDto toDto(Order order) {
        List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                .map(item -> new OrderItemResponseDto(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        Long userId = null;
        if (order.getUser() != null) {
            userId = order.getUser().getId();
        } else {
            System.out.println("❗ order.getUser()가 null입니다. orderId=" + order.getId());
        }

        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getTotalPrice(),
                order.getFinalPrice(),
                itemDtos,
                userId,
                order.getAddress()
        );
    }
}