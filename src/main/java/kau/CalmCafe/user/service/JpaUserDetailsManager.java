package kau.CalmCafe.user.service;

import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;

    public JpaUserDetailsManager(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    // 새로운 사용자 생성 함수
    @Override
    public void createUser(UserDetails user) {
        log.info("try create user: {}", user.getUsername());

        // 동일한 username이 이미 존재하는지 확인
        if (this.userExists(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            // UserDetails -> CustomUserDetail 캐스팅 후 새 엔티티 생성
            userRepository.save(((CustomUserDetails) user).newEntity());
        } catch (ClassCastException e) {
            log.error("failed to cast to {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 사용자 정보 갱신
    @Override
    public void updateUser(UserDetails user) {}

    // 사용자 정보 삭제
    @Override
    public void deleteUser(String username) {}

    // 비밀번호 변경
    @Override
    public void changePassword(String oldPassword, String newPassword) {}

    // 동일한 username이 존재하는지 확인
    @Override
    public boolean userExists(String username) {
        log.info("check if user: {} exists", username);
        return this.userRepository.existsByUsername(username);
    }

    // Spring Security 내부에서 사용자 인증 시 호출 (사용자 조회 후, User -> CustomUserDetails 변환)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        return CustomUserDetails.fromUser(user);
    }
}
