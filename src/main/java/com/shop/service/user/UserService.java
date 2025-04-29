package com.shop.service.user;

import com.shop.dto.user.UserRequestDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.user.User;
import com.shop.exception.user.UserNotFoundException;
import com.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 회원 가입
     */
    public Long register(UserRequestDto dto) {
        validateDuplicateUsername(dto.getUsername());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = new User(
                dto.getUsername(),
                encodedPassword,
                dto.getName(),
                dto.getEmail()
        );

        return userRepository.save(user).getId();
    }

    /**
     * 전체 사용자 조회
     */
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                )).toList();
    }

    /**
     * 단일 사용자 조회
     */
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail()
        );
    }

    /**
     * 로그인 유효성 검증
     */
    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("아이디가 존재하지 않습니다."));

        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        userRepository.findByUsername(dto.getUsername())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
                });

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

    /**
     * 사용자 삭제
     */
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("존재하지 않는 사용자입니다.");
        }
        userRepository.deleteById(id);
    }

    /**
     * 사용자 이름으로 ID 조회
     */
    public Long findIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."))
                .getId();
    }

    /**
     * 중복된 아이디 검사
     */
    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }
}
