package com.shop.service.user;

import com.shop.dto.user.LoginRequestDto;
import com.shop.dto.user.UserRequestDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.user.User;
import com.shop.exception.user.UserNotFoundException;
import com.shop.repository.user.UserRepository;
import com.shop.security.JwtUtil;
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
        User user = User.create(
                dto.getUsername(),
                encodePassword(dto.getPassword()),
                dto.getName(),
                dto.getEmail()
        );
        return userRepository.save(user).getId();
    }

    /**
     * 관리자 회원 가입
     */
    public Long registerAdmin(UserRequestDto dto) {
        validateDuplicateUsername(dto.getUsername());
        User admin = User.createAdmin(
                dto.getUsername(),
                encodePassword(dto.getPassword()),
                dto.getName(),
                dto.getEmail()
        );
        return userRepository.save(admin).getId();
    }

    /**
     * 로그인
     */
    public String login(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        return JwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }

    /**
     * 전체 사용자 조회
     */
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 단일 사용자 조회
     */
    public UserResponseDto findById(Long id) {
        return toDto(findUserById(id));
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = findUserById(id);

        userRepository.findByUsername(dto.getUsername())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
                });

        user.updateInfo(
                encodePassword(dto.getPassword()),
                dto.getName(),
                dto.getEmail(),
                dto.getUsername()
        );

        return toDto(user);
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
     * 로그인 유효성 검증
     */
    public boolean validateUser(String username, String rawPassword) {
        User user = findUserByUsername(username);
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    /**
     * 사용자 이름으로 ID 조회
     */
    public Long findIdByUsername(String username) {
        return findUserByUsername(username).getId();
    }

    /**
     * 사용자 객체 조회 (by username)
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 사용자 객체 조회 (by id)
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }

    /**
     * 중복된 아이디 검사
     */
    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }

    /**
     * 비밀번호 암호화
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * User → UserResponseDto 변환
     */
    private UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail()
        );
    }
}