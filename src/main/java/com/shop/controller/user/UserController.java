package com.shop.controller.user;

import com.shop.dto.user.UserRequestDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.service.user.UserService;
import com.shop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> register(@RequestBody UserRequestDto dto) {
        Long userId = userService.register(dto);
        return ResponseEntity.ok(userId);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto dto) {
        boolean isValidUser = userService.validateUser(dto.getUsername(), dto.getPassword());

        if (!isValidUser) {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // JWT 토큰 생성
        String token = JwtUtil.generateToken(dto.getUsername());
        return ResponseEntity.ok(token);  // JWT 토큰 반환
    }

    //테스트
    @GetMapping("/me")
    public ResponseEntity<String> getMyInfo() {
        return ResponseEntity.ok("Hello, this is a protected resource.");
    }

    // 전체 사용자 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // ID로 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}