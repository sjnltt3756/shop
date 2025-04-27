package com.shop.controller.order;

import com.shop.dto.order.OrderRequestDto;
import com.shop.dto.order.OrderResponseDto;
import com.shop.security.JwtUtil;
import com.shop.service.order.OrderService;
import com.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestHeader("Authorization") String authHeader,
                                            @RequestBody OrderRequestDto dto) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        Long orderId = orderService.createOrder(userId, dto);
        return ResponseEntity.ok(orderId);
    }

    // 주문 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrderById(@RequestHeader("Authorization") String authHeader,
                                                          @PathVariable Long orderId) {
        // 토큰에서 사용자 정보 추출
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        // 주문 조회
        OrderResponseDto order = orderService.findOrderById(orderId);

        // 확인: 해당 주문이 현재 사용자의 것인지 확인
        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("이 주문은 다른 사용자의 주문입니다.");
        }

        return ResponseEntity.ok(order);
    }

    // 나의 주문 목록 조회
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDto>> findMyOrders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        return ResponseEntity.ok(orderService.findMyOrders(userId));
    }

    // 주문 취소
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable Long orderId) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(token);
        Long userId = userService.findIdByUsername(username);

        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.noContent().build();
    }
}