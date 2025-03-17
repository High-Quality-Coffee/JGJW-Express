package com.zgzg.gateway.infrastructure.jwt;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import com.zgzg.gateway.infrastructure.jwt.AuthPermissionFilter.Config;


import java.util.List;

@Slf4j
@Component
public class AuthPermissionFilter extends AbstractGatewayFilterFactory<Config> {

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthPermissionFilter(JWTUtil jwtUtil) {
        super(AuthPermissionFilter.Config.class);
        this.jwtUtil = jwtUtil;
    }

    // 필터에 필요한 설정이 있다면 여기에 추가
    public static class Config {
    }

    //사용자의 권한을 확인하는 필터
    @Override
    public GatewayFilter apply(AuthPermissionFilter.Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            // 요청 객체에서 Access 토큰을 가져온다.
            List<String> accessToken = jwtUtil.getHeaderToken(request, "access");

            // Access 토큰이 없다면 에러를 반환.
            if (accessToken == null || accessToken.isEmpty()){
                throw new BaseException(Code.TOKEN_NOT_EXISTS);
            }

            // Access 토큰 검증
            if(!jwtUtil.isValidToken(accessToken.get(0))){
                if(jwtUtil.isExpired(accessToken.get(0))){
                    throw new BaseException(Code.EXPIRED_TOKEN);
                } else {
                    throw new BaseException(Code.INVALID_TOKEN);
                }
            }

            // 사용자 정보를 헤더에 추가
            String username = jwtUtil.getUsername(accessToken.get(0));
            String role = jwtUtil.getRole(accessToken.get(0));

            // 사용자 정보를 헤더에 추가
            ServerHttpRequest authRequest = request.mutate()
                    .header("Auth", "true")
                    .header("username", username)
                    .header("role", role)
                    .build();

            return chain.filter(exchange.mutate().request(authRequest).build());
        };
    }
}

