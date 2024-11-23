package kau.CalmCafe.user.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.stream.Stream;
import kau.CalmCafe.global.UuidRepository;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.entity.Uuid;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.user.converter.UserConverter;
import kau.CalmCafe.user.domain.Marriage;
import kau.CalmCafe.user.domain.RefreshToken;
import kau.CalmCafe.user.domain.Role;
import kau.CalmCafe.user.domain.Sex;
import kau.CalmCafe.user.domain.Survey;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.dto.JwtDto;
import kau.CalmCafe.user.dto.UserRequestDto.UserReqDto;
import kau.CalmCafe.user.dto.UserRequestDto.UserSurveyInfo;
import kau.CalmCafe.user.jwt.JwtTokenUtils;
import kau.CalmCafe.user.repository.RefreshTokenRepository;
import kau.CalmCafe.user.repository.SurveyRepository;
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
    private static final int SURVEY_INFO_ADDITIONAL_ENTRY_COUNT = 9;
    private static final int SURVEY_INFO_ADDITIONAL_POINT = 300;
    private static final int SURVEY_INFO_ESSENTIAL_POINT = 2000;

    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final JpaUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UuidRepository uuidRepository;

    @Transactional
    public Role getRole(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> GeneralException.of(ErrorCode.USER_NOT_FOUND));

        return user.getRole();
    }

    @Transactional
    public Role changeRole(User user) {
        user.changeRole();
        userRepository.save(user);

        return user.getRole();
    }

    @Transactional
    public Long saveSurveyInfo(User user, UserSurveyInfo userSurveyInfo){
        boolean hasCompletedSurvey = surveyRepository.existsByUserId(user.getId());
        if (hasCompletedSurvey) {
            throw new GeneralException(ErrorCode.ALREADY_SURVEY);
        }

        Survey survey = UserConverter.saveSurvey(user, userSurveyInfo);
        surveyRepository.save(survey);

        // 추가 필드 입력 여부 체크
        long surveyInfoBlankCount = Stream.of(
                userSurveyInfo.getLocation(),
                userSurveyInfo.getMarriage(),
                userSurveyInfo.getHobby(),
                userSurveyInfo.getFavoriteMenu(),
                userSurveyInfo.getCafeUsingPurpose(),
                userSurveyInfo.getCafeChooseCause(),
                userSurveyInfo.getCafeVisitedFrequency(),
                userSurveyInfo.getIsUsingSNS(),
                userSurveyInfo.getConvenienceFacilityPrefer()
        ).filter(String::isBlank).count();


        int surveyInfoEntryCount = SURVEY_INFO_ADDITIONAL_ENTRY_COUNT - (int) surveyInfoBlankCount;

        user.addPoint(SURVEY_INFO_ESSENTIAL_POINT + SURVEY_INFO_ADDITIONAL_POINT * surveyInfoEntryCount);

        return survey.getId();
    }

    public User findByUserName(String userName){
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND_BY_USERNAME));
    }

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

    @Transactional
    public void logout(HttpServletRequest request) {
        // Access Token Value 추출
        String accessTokenValue = request.getHeader("Authorization").split(" ")[1];

        // Access Token을 통해 username 찾기
        String username = jwtTokenUtils.parseClaims(accessTokenValue).getSubject();

        // username을 통해 Refresh Token 삭제
        if (refreshTokenRepository.existsById(username)) {
            refreshTokenRepository.deleteById(username);
        }
        else {
            throw GeneralException.of(ErrorCode.WRONG_REFRESH_TOKEN);
        }
    }

    // Access Token & Refresh Token 재발급
    @Transactional
    public JwtDto reissue(HttpServletRequest request) {
        // Access Token Value 추출
        String accessTokenValue = request.getHeader("Authorization").split(" ")[1];

        // Access Token을 통해 username 찾기
        String username = jwtTokenUtils.parseClaims(accessTokenValue).getSubject();

        log.info("Access Token Value: {}", accessTokenValue);

        // username을 통해 Refresh Token 객체 찾기
        RefreshToken refreshToken = refreshTokenRepository.findById(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.WRONG_REFRESH_TOKEN));

        log.info("Refresh Token 객체: {}", refreshToken);

        // Refresh Token의 사용자 정보 찾기
        UserDetails userDetails = manager.loadUserByUsername(refreshToken.getId());

        // Access Token & Refresh Token 재발급
        JwtDto jwt = jwtTokenUtils.generateToken(userDetails);

        log.info("reissue: Refresh Token 재발급 완료");

        // Refresh Token 정보 갱신
        refreshToken.updateRefreshToken(jwt.getRefreshToken());

        // 재발급 JWT 파싱을 통해 Claim 객체 찾기
        Claims refreshTokenClaims = jwtTokenUtils.parseClaims(jwt.getRefreshToken());

        // Refresh Token 유효 기간 찾기
        Long validPeriod = refreshTokenClaims.getExpiration().toInstant().getEpochSecond()
                - refreshTokenClaims.getIssuedAt().toInstant().getEpochSecond();

        // Refresh Token 유효 기간 갱신
        refreshToken.updateTtl(validPeriod);

        // DB에 갱신된 Refresh Token 저장
        refreshTokenRepository.save(refreshToken);

        // DB에 갱신된 Refresh Token 저장 여부 확인
        if (!refreshTokenRepository.existsById(refreshToken.getId())) {
            throw GeneralException.of(ErrorCode.WRONG_REFRESH_TOKEN);
        }

        return jwt;
    }

    public Boolean checkMemberByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
