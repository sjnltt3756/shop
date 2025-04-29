package com.shop.service.admin;

import com.shop.dto.order.OrderItemResponseDto;
import com.shop.dto.order.OrderResponseDto;
import com.shop.entity.order.Order;
import com.shop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    /**
     * 전체 주문 조회
     */
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDto)
                .toList();
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

        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                itemDtos,
                order.getUser().getId()
        );
    }
}