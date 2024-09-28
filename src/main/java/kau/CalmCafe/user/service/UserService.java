package kau.CalmCafe.user.service;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import kau.CalmCafe.global.UuidRepository;
import kau.CalmCafe.global.entity.Uuid;
import kau.CalmCafe.user.converter.UserConverter;
import kau.CalmCafe.user.domain.RefreshToken;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.dto.JwtDto;
import kau.CalmCafe.user.dto.UserRequestDto.UserReqDto;
import kau.CalmCafe.user.jwt.JwtTokenUtils;
import kau.CalmCafe.user.repository.RefreshTokenRepository;
import kau.CalmCafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JpaUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UuidRepository uuidRepository;

    @Transactional
    public User createUser(UserReqDto userReqDto) {
        // 사용자의 고유 식별자인 uuid 생성
        Uuid uuid = Uuid.generateUuid();

        // "user" 문자열을 붙여 고유 닉네임 생성
        String nick = "user" + uuid.getUuid();

        // 생성된 uuid 저장
        uuidRepository.save(uuid);

        // userReqDto 데이터를 User 엔티티로 변환하여 nick과 함께 DB에 저장
        User newUser = userRepository.save(UserConverter.saveUser(userReqDto, nick));

        // Spring Security와 연동을 위해 인증, 인가 정보 최신화
        manager.loadUserByUsername(userReqDto.getUsername());

        return newUser;
    }

    @Transactional
    public JwtDto jwtMakeSave(String username){

        // username을 통한 UserDetail 생성
        UserDetails details = manager.loadUserByUsername(username);

        // userDetail을 통한 JWT 생성
        JwtDto jwt = jwtTokenUtils.generateToken(details);

        log.info("accessToken: {}", jwt.getAccessToken());
        log.info("refreshToken: {} ", jwt.getRefreshToken());

        // DB에 Refresh 토큰의 발급 시간과 만료 기간을 통한 유효 기간 계산
        Claims refreshTokenClaims = jwtTokenUtils.parseClaims(jwt.getRefreshToken());
        Long validPeriod
                = refreshTokenClaims.getExpiration().toInstant().getEpochSecond()
                - refreshTokenClaims.getIssuedAt().toInstant().getEpochSecond();

        // DB 내 기존 Refresh 토큰 존재 여부 확인 후, 있으면 삭제
        Optional<RefreshToken> existingToken = refreshTokenRepository.findById(username);
        if (existingToken.isPresent()) {
            refreshTokenRepository.deleteById(username);
        }

        // 새로운 Refresh 토큰 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(username)
                        .ttl(validPeriod)
                        .refreshToken(jwt.getRefreshToken())
                        .build()
        );

        return jwt;
    }

    public Boolean checkMemberByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
