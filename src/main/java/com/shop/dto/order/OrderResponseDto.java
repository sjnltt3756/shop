package com.shop.dto.order;

import com.shop.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private final Long id;
    private final String status;
    private final int totalAmount;
    private final int totalPrice;
    private final int finalPrice;
    private final List<OrderItemResponseDto> orderItems;
    private final Long userId;
    private Address address;
}