package com.zgzg.gateway.infrastructure.jwt;



import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;


//JWTUtil 0.12.3 version
@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

   //HTTP 헤더에서 토큰을 추출
    public List<String> getHeaderToken(ServerHttpRequest request, String headerName) {
        return request.getHeaders().getOrDefault(headerName, Collections.emptyList());
    }

    //토큰의 유효성을 확인
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;  // 서명 검증 성공
        } catch (Exception e) {
            return false;  // 서명 실패 또는 기타 예외 발생
        }
    }

    //아래 2개의 메서드는 토큰을 검증하는 로직을 담고 있다. getUsername, isExpired
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username",String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

}


