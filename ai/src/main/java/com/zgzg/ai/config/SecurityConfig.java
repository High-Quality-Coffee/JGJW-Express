package com.zgzg.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
	//AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (POST 요청 허용)
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/api/v1/gemini/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()  // 회원가입, 로그인은 인증 없이 가능
				.anyRequest().permitAll())
			.formLogin(login -> login.disable())  // 기본 로그인 폼 비활성화
			.httpBasic(basic -> basic.disable()); // HTTP Basic 인증 비활성화

		http
			.logout((auth) -> auth.disable()); // 기본 로그아웃 필터 비활성화

		//세션 설정
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
