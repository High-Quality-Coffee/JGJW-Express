package com.zgzg.user.Infrastructure.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.user.domain.model.RefreshToken;
import com.zgzg.user.domain.model.User;
import com.zgzg.user.domain.repository.RefreshRepository;
import com.zgzg.user.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.userRepository=userRepository;
        //spring security는 대부분의 로직이 필터 단에서 동작하게 된다. 로그인 또한, 필터에서 처리되고, (자동으로 엔드포인트는 "/login" 이 된다.)
        //UsernamePasswordAuthenticationFilter에서 매핑되어 처리된다. 이 필터를 상속받아 LoginFilter를 만들게 된다.
        //security에서 설정해주는 기본 url("/login")을 /api/v1/login 으로 변경
        setFilterProcessesUrl("/api/v1/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청으로 부터 username, password를 추출한다.
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
        String username = null;
        String password = null;

        // JSON 형식인지 확인
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            try {
                // JSON 데이터를 읽어서 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                Map requestMap = objectMapper.readValue(request.getInputStream(), Map.class);

                username = String.valueOf(requestMap.get("username"));
                password = String.valueOf(requestMap.get("password"));
            } catch (IOException e) {
                throw new AuthenticationServiceException("Failed to parse JSON request", e);
            }
        } else {
            // 기본 form-urlencoded 방식 처리
            username = obtainUsername(request);
            password = obtainPassword(request);
        }

        if (username == null || password == null) {
            throw new AuthenticationServiceException("Username or Password is missing");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);

    }

    //로그인 성공시 실행하는 메서드 (여기서 jwt를 발급하면 된다)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException{
        //유저 이름 찾기
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        if (!iterator.hasNext()) {
            throw new BaseException("User not found");
        }

        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        Long id = user.getId();

        String access = jwtUtil.createJwt("access",username, role, id, 600000L*60*24*100); // 24시간 *100 = 100일. 테스트를 위해 기한 늘림
        String refresh = jwtUtil.createJwt("refresh", username, role,id,86400000L*100); // 24시간 *100 = 100일

        RefreshTokenSave(username,refresh,86400000L*100);

        // 로그인 성공
        ResponseEntity<ApiResponseData<String>> responseBody = ResponseEntity.ok().body(ApiResponseData.success("로그인 성공하였습니다."));

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 변환 후 출력
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseBody.getBody()));

    }

    //로그인 실패시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException failed) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 실패 응답 객체 생성
        ResponseEntity<ApiResponseData<String>> responseBody = ResponseEntity.badRequest().body(ApiResponseData.failure(Code.LOGIN_FAILED.getCode(), "로그인에 실패하셨습니다."));

        // JSON 변환 후 출력
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseBody.getBody()));
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
//        cookie.setSecure(true); // https일 경우 설정
//        cookie.setPath("/"); // 쿠키의 적용 범위 설정

        //js로 쿠키에 접근 못하게 함
        cookie.setHttpOnly(true);

        return cookie;
    }

    public void RefreshTokenSave(String username, String refresh,Long expiredMs){
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setRefresh(refresh);
        refreshToken.setExpiration(date.toString());

        refreshRepository.save(refreshToken);
    }
}

