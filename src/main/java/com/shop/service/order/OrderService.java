package com.shop.service.order;

import com.shop.dto.order.*;
import com.shop.entity.order.Order;
import com.shop.entity.order.OrderItem;
import com.shop.entity.product.Product;
import com.shop.entity.user.User;
import com.shop.exception.order.OrderNotFoundException;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.exception.user.UserNotFoundException;
import com.shop.repository.order.OrderRepository;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public Long createOrder(Long userId, OrderRequestDto dto) {
        User user = findUser(userId);
        List<OrderItem> orderItems = createOrderItems(dto);
        int totalAmount = calculateTotal(orderItems);

        Order order = Order.create(user, orderItems, dto.getStatus(), totalAmount);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        Order savedOrder = orderRepository.save(order);
        order.updateStatus("COMPLETE");
        return savedOrder.getId();
    }

    /**
     * 주문 조회
     */
    @Transactional(readOnly = true)
    public OrderResponseDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("존재하지 않는 주문입니다."));
        return toDto(order);
    }

    /**
     * 나의 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDto> findMyOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("존재하지 않는 주문입니다."));

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 주문만 취소할 수 있습니다.");
        }

        if ("CANCELED".equalsIgnoreCase(order.getStatus())) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }

        order.updateStatus("CANCELED");
    }

    /**
     * 사용자 조회
     */
    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }

    /**
     * 주문 항목 생성
     */
    private List<OrderItem> createOrderItems(OrderRequestDto dto) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDto itemDto : dto.getOrderItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

            OrderItem orderItem = OrderItem.create(null, product, itemDto.getQuantity(), itemDto.getPrice());
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    /**
     * 총 금액 계산
     */
    private int calculateTotal(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToInt(item -> item.getQuantity() * item.getPrice())
                .sum();
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
