package com.shop.service.user;

import com.shop.dto.user.UserRequestDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.User;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // BCryptPasswordEncoder 사용

    public Long register(UserRequestDto dto) {
        validateDuplicateUsername(dto.getUsername());

        String encodedPassword = passwordEncoder.encode(dto.getPassword()); // 비밀번호 암호화

        User user = new User(
                dto.getUsername(),
                encodedPassword,  // 암호화된 비밀번호 저장
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

    // 로그인 기능 추가
    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        return passwordEncoder.matches(password, user.getPassword());  // 비밀번호 일치 여부 확인
    }
    // 유저 정보 업데이트
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 자기 자신이 아닌 다른 사용자와 username이 중복되는지 확인
        userRepository.findByUsername(dto.getUsername())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
                });

        // updateInfo 메서드로 수정
        user.updateInfo(
                passwordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getEmail(),
                dto.getUsername()
        );

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail()
        );
    }
    // 유저 삭제
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        userRepository.deleteById(id);
    }
}