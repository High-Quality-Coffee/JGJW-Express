package com.zgzg.hub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class TestSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 기본 보안 설정
    http
        .csrf(csrf -> csrf.disable())  // CSRF 비활성화
        .formLogin(login -> login.disable())  // 기본 로그인 폼 비활성화
        .httpBasic(basic -> basic.disable())  // HTTP Basic 인증 비활성화
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // 세션 비활성화
    return http.build();
  }
}