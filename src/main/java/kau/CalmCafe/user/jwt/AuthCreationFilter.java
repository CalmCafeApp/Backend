package kau.CalmCafe.user.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthCreationFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;

    // 실제 JWT를 검사하고 인증을 처리하는 함수
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        // AUTHORIZATION 헤더에서 String 값 추출 (JWT 토큰)
        String authHeader
                = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 추출한 String이 존재하고, "Bearer "로 시작하는지 확인 (JWT 토큰 표준 형식)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 공백을 기준으로 String을 나누고, 두번째 요소에 해당하는 JWT 회수
            String token = authHeader.split(" ")[1];

            // 현재 스레드에서 인증된 사용자 정보를 담는 컨테이너 생성
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication;

            // 경로 /users/reissue 에 대한 요청일 경우 (토큰 재발급)
            if (request.getServletPath().equals("/users/reissue")) {
                // 익명 사용자에 대한 인증 객체 생성
                authentication =
                        new AnonymousAuthenticationToken(
                                "key",
                                "anonymousUser",
                                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
                        );
            }
            // 그 외 경로에 대한 요청일 경우 (토큰 인증)
            else {
                // JWT에서 Claim 정보 추출
                Claims claims = jwtTokenUtils.parseClaims(token);

                // 인증된 사용자 정보와 권한을 담은 인증 객체 생성
                authentication =
                        new UsernamePasswordAuthenticationToken(
                                CustomUserDetails.builder()
                                        .username(claims.getSubject()) // Claim 내 사용자 이름 정보 기반
                                        .build(),
                                token,
                                jwtTokenUtils.getAuthFromClaims(claims)
                        );
            }

            // 현재 스레드에서의 인증 정보 저장
            context.setAuthentication(authentication);

            // Spring Security에서 인증 정보를 유지, 관리하도록 설정
            SecurityContextHolder.setContext(context);

            log.info("{} 인증 객체 생성 완료", context.getAuthentication().getName());
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
