package com.shop.controller.user;

import com.shop.dto.user.LoginRequestDto;
import com.shop.dto.user.UserRequestDto;
import com.shop.dto.user.UserResponseDto;
import com.shop.entity.user.User;
import com.shop.exception.user.UserNotFoundException;
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

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<Long> register(@RequestBody UserRequestDto dto) {
        Long userId = userService.register(dto);
        return ResponseEntity.ok(userId);
    }

    /**
     * 로그인 - 토큰 발급
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(token);
    }

//    //테스트
//    @GetMapping("/me")
//    public ResponseEntity<String> getMyInfo() {
//        return ResponseEntity.ok("Hello, this is a protected resource.");
//    }

    /**
     * 전체 사용자 조회
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * ID로 사용자 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * 유저 정보 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    /**
     * 유저 정보 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 관리자 회원가입
     */
    @PostMapping("/signup/admin")
    public ResponseEntity<Long> registerAdmin(@RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.registerAdmin(dto));
    }
}