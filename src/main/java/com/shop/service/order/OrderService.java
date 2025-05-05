package com.shop.service.order;

import com.shop.dto.order.*;
import com.shop.entity.coupon.Coupon;
import com.shop.entity.coupon.UserCoupon;
import com.shop.entity.order.Order;
import com.shop.entity.order.OrderItem;
import com.shop.entity.product.Product;
import com.shop.entity.user.User;
import com.shop.exception.coupon.CouponAlreadyUsedException;
import com.shop.exception.coupon.CouponExpiredException;
import com.shop.exception.coupon.CouponNotFoundException;
import com.shop.exception.coupon.CouponNotOwnedException;
import com.shop.exception.order.OrderNotFoundException;
import com.shop.exception.product.ProductNotFoundException;
import com.shop.exception.user.UserNotFoundException;
import com.shop.repository.coupon.CouponRepository;
import com.shop.repository.coupon.UserCouponRepository;
import com.shop.repository.order.OrderRepository;
import com.shop.repository.product.ProductRepository;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserCouponRepository userCouponRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public Long createOrder(Long userId, OrderRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        List<OrderItem> orderItems = createOrderItems(dto); // 주문 항목 생성
        int totalPrice = calculateTotal(orderItems); // 총 금액 계산
        int totalAmount = calculateTotalAmount(orderItems); // 총 상품 개수 계산

        int finalPrice = totalPrice;

        UserCoupon userCoupon = null;
        if (dto.getUserCouponId() != null) {
            userCoupon = userCouponRepository.findById(dto.getUserCouponId())
                    .orElseThrow(() -> new CouponNotFoundException("쿠폰이 존재하지 않습니다."));

            if (!userCoupon.getUser().equals(user)) {
                throw new CouponNotOwnedException("본인의 쿠폰만 사용 가능합니다.");
            }

            if (userCoupon.isUsed()) {
                throw new CouponAlreadyUsedException("이미 사용된 쿠폰입니다.");
            }

            if (!userCoupon.getCoupon().isEnabled()) {
                throw new IllegalStateException("비활성화된 쿠폰입니다.");
            }

            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(userCoupon.getCoupon().getStartAt()) || now.isAfter(userCoupon.getCoupon().getEndAt())) {
                throw new CouponExpiredException("쿠폰 유효기간이 아닙니다.");
            }

            // 쿠폰 할인 적용
            int discountAmount = userCoupon.getCoupon().getDiscountAmount();
            finalPrice = Math.max(0, totalPrice - discountAmount);
            userCoupon.use();
        }

        // Order 생성
        Order order = Order.create(user, orderItems, "ORDERED", totalAmount, totalPrice, finalPrice, userCoupon);

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    /**
     * 주문 조회
     */
    @Transactional(readOnly = true)
    public OrderResponseDto findOrderById(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("존재하지 않는 주문입니다."));
            return toDto(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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

            OrderItem orderItem = OrderItem.create(null, product, itemDto.getQuantity(), product.getPrice());
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    /**
     * 총 금액 계산
     */
    private int calculateTotal(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToInt(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }

    /**
     * 총 상품 개수 계산
     */
    private int calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
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
                order.getTotalPrice(),
                order.getFinalPrice(),
                itemDtos,
                order.getUser().getId()
        );
    }
}
