package com.zgzg.user.Infrastructure.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.zgzg.common.security.GlobalSecurityContextFilter;
import com.zgzg.user.Infrastructure.jwt.CustomLogoutFilter;
import com.zgzg.user.Infrastructure.jwt.JWTFilter;
import com.zgzg.user.Infrastructure.jwt.JWTUtil;
import com.zgzg.user.Infrastructure.jwt.LoginFilter;
import com.zgzg.user.domain.repository.RefreshRepository;
import com.zgzg.user.domain.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	//AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	private final RefreshRepository refreshRepository;
	private final GlobalSecurityContextFilter globalSecurityContextFilter;
	private final UserRepository userRepository;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// Custom LoginFilter 등록
		LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,
			refreshRepository, userRepository);
		loginFilter.setFilterProcessesUrl("/api/v1/login"); // 엔드포인트를 /api/login으로 변경

		http
			.cors((cors) -> cors
				.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration configuration = new CorsConfiguration();
						configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
						configuration.setAllowedMethods(Collections.singletonList("*"));
						configuration.setAllowCredentials(true);
						configuration.setAllowedHeaders(Collections.singletonList("*"));
						configuration.setMaxAge(3600L);

						configuration.setExposedHeaders(Arrays.asList("Authorization", "access"));
						return configuration;
					}

				}));

		http
			.securityMatcher("/**")
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/v1/join/**", "/api/v1/login", "/api/v1/delivery/users/**",
					"/swagger-ui/index.html/**","/user/v3/api-docs/**","/v3/api-docs/**").permitAll()
				.anyRequest().authenticated()
			)
			.csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (POST 요청 허용)
			.formLogin(login -> login.disable())  // 기본 로그인 폼 비활성화
			.httpBasic(basic -> basic.disable()) // HTTP Basic 인증 비활성화
			.logout((auth) -> auth.disable()) // 기본 로그아웃 필터 비활성화
			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
			.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository,
				userRepository), UsernamePasswordAuthenticationFilter.class)
			.addFilterAt(globalSecurityContextFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
