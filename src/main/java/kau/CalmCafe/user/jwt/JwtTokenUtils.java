package kau.CalmCafe.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kau.CalmCafe.user.dto.JwtDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// JWT 관련 기능들을 넣어두기 위한 기능성 클래스
@Slf4j
@Component
public class JwtTokenUtils {
    private final Key signingKey;
    private final JwtParser jwtParser;
    private final int accessExpirationTime;
    private final int refreshExpirationTime;

    // JWT 토큰 생성과 검증에 필요한 핵심 정보 설정하는 생성자
    public JwtTokenUtils(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.accessExpirationTime}") int accessExpirationTime,
            @Value("${jwt.refreshExpirationTime}") int refreshExpirationTime
    )
    {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtParser = Jwts.parserBuilder().setSigningKey(this.signingKey).build();
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    // 주어진 사용자 정보를 통한 Access Token & Refresh Token 생성
    public JwtDto generateToken(UserDetails userDetails) {
        // 사용자 권한 추출
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // Claim: JWT에 포함될 정보 단위

        // AccessToken을 구성할 Claims 객체 생성
        Claims accessTokenClaims = Jwts.claims()
                .setSubject(userDetails.getUsername()) // Subject 설정
                .setIssuedAt(java.sql.Date.from(Instant.now())) // 발급 시간 설정
                .setExpiration(java.sql.Date.from(Instant.now().plusSeconds(accessExpirationTime))); // 만료 시간 설정

        // AccessToken 문자열 생성
        String accessToken = Jwts.builder()
                .setClaims(accessTokenClaims)
                .claim("authorities", authorities)
                .signWith(signingKey)
                .compact();

        // RefreshToken을 구성할 Claims 객체 생성
        Claims refreshTokenClaims = Jwts.claims()
                .setIssuedAt(java.sql.Date.from(Instant.now())) // 발급 시간 설정
                .setExpiration(Date.from(Instant.now().plusSeconds(refreshExpirationTime))); // 만료 시간 설정

        // RefreshToken 문자열 생성
        String refreshToken = Jwts.builder()
                .setClaims(refreshTokenClaims)
                .signWith(signingKey)
                .compact();

        // AccessToken과 RefreshToken을 JwtDto에 담아 반환
        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT를 인자로 받아 해석 후, 사용자 정보, 권한 등 Claim 추출
    public Claims parseClaims(String token) {

        // JWT Parser: JWT 서명을 검증하고, 토큰의 내용을 해석하는 데 사용되는 객체
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    // Claim에서 권한 정보를 추출하여 사용자 권한을 관리하는 Collection 반환
    public Collection<? extends GrantedAuthority> getAuthFromClaims(Claims claims){
        // Claims 객체 내 권한(authorities) 정보 가져오기
        String authoritiesString = (String) claims.get("authorities");

        // 권한(authorities) 정보를 콤마 기준으로 분할하여 Stream을 생성
        return Arrays.stream(authoritiesString.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
