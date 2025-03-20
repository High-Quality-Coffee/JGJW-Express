package com.zgzg.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class GlobalSecurityConfig {

    private final GlobalSecurityContextFilter globalSecurityContextFilter;


    public SecurityFilterChain globalSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())  // 로그인 페이지 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())  // HTTP 기본 인증 비활성화
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/join/**", "/api/v1/login").permitAll()  // 회원가입, 로그인은 인증 없이 가능
                        .anyRequest().permitAll())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(globalSecurityContextFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
