package com.shop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "my-very-very-very-very-secret-key"; // 비밀 키
    private static final long EXPIRATION_TIME = 86400000L; // 토큰 만료 시간 (1일)

    // JWT 생성
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()) // SECRET_KEY를 바이트 배열로 사용
                .compact();
    }

    // JWT 검증
    public static boolean validateToken(String token) {
        try {
            // parseClaimsJws를 사용할 때, 새로운 방식으로 구현
            Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes()) // SECRET_KEY를 바이트 배열로 제공
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT에서 사용자 정보 추출
    public static String extractUsername(String token) {
        // Claims를 얻어와서 username 추출
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes()) // SECRET_KEY를 바이트 배열로 제공
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // 클레임에서 사용자 정보 (subject) 반환
    }
}