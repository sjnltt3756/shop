package com.shop.service.admin;

import com.shop.dto.user.UserResponseDto;
import com.shop.entity.user.User;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    /**
     * 전체 회원 조회
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                )).toList();
    }

    /**
     * 회원 삭제
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
