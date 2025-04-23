package com.shop.dto.user;

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
}
