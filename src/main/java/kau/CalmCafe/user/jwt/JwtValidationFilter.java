package kau.CalmCafe.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kau.CalmCafe.global.api_payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Spring Security에서 매 요청마다 JWT의 유효성 검사를 진행하는 필터 클래스
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;

    // 메세지를 JSON 형식으로 변환하여, HTTP 응답을 위한 라이브러리
    private final ObjectMapper objectMapper;

    // 매 HTTP 요청마다 JWT 토큰을 확인하여 유효성을 검사하는 핵심 함수
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // AUTHORIZATION 헤더에서 String 값 추출 (JWT 토큰)
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("authHeader 확인: " + authHeader);

        // 추출한 String이 존재하고, "Bearer "로 시작하는지 확인 (JWT 토큰 표준 형식)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 공백을 기준으로 String을 나누고, 두번째 요소에 해당하는 JWT 회수
            String token = authHeader.split(" ")[1];

            log.info("token 검증 시작: {}", token);

            // 토큰을 Parsing 하여 유효성 검사
            try {
                jwtTokenUtils.parseClaims(token);
            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                log.info("JWT 서명이 잘못되었습니다.");
                jwtExceptionHandler(response, ErrorCode.TOKEN_INVALID);
                return;
            } catch (ExpiredJwtException e) {
                log.info("JWT 토큰이 만료되었습니다.");
                jwtExceptionHandler(response, ErrorCode.TOKEN_EXPIRED);
                return;
            } catch (UnsupportedJwtException e) {
                log.info("지원되지 않는 토큰입니다.");
                jwtExceptionHandler(response, ErrorCode.TOKEN_INVALID);
                return;
            } catch (IllegalArgumentException e) {
                log.info("잘못된 토큰입니다.");
                jwtExceptionHandler(response, ErrorCode.TOKEN_INVALID);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // JWT가 유효하지 않을 경우, HTTP 응답을 통해 클라이언트에게 에러 메세지를 반환하는 함수
    public void jwtExceptionHandler(HttpServletResponse response, ErrorCode error) {
        // 에러에 따른 HTTP 상태 코드 설정
        response.setStatus(error.getHttpStatus().value());

        // JSON 형식으로 응답
        response.setContentType("application/json");

        // UTF-8로 인코딩
        response.setCharacterEncoding("UTF-8");

        log.info("필터 에러 커스텀");

        // JSON 형식으로 에러 메세지 반환
        try {
            objectMapper.writeValue(response.getWriter(), error.getReason());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
