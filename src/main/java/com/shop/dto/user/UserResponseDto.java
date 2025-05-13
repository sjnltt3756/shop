package com.shop.dto.user;

import com.shop.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final Address address;
}
