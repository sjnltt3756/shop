package com.shop.dto.user;

import com.shop.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true) // final 필드에 강제 초기화 허용
public class UserRequestDto {
    private final String username;
    private final String password;
    private final String name;
    private final String email;

    private String city;      // 🏙️
    private String street;    // 🛣️
    private String zipcode;   // 🏷️

    public Address toAddress() {
        return new Address(city, street, zipcode);
    }
}
