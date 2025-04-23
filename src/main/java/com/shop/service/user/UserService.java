package com.shop.service.user;

import com.shop.dto.user.UserRequestDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.User;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long register(UserRequestDto dto) {
        validateDuplicateUsername(dto.getUsername());

        User user = new User(
                dto.getUsername(),
                dto.getPassword(), // 추후 BCrypt 암호화 예정
                dto.getName(),
                dto.getEmail()
        );

        return userRepository.save(user).getId();
    }

    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                )).toList();
    }

    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                ))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
