package kau.CalmCafe.user.jwt;

import kau.CalmCafe.user.domain.Role;
import kau.CalmCafe.user.domain.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {
    // 사용자 고유 식별자
    @Getter
    private Long id;

    // 사용자 이메일 주소
    @Getter
    private String email;

    // 외부에서 제공한 정보 (Kakao)
    @Getter
    private String provider;

    // 사용자 아이디
    private String username;

    // 사용자 별명
    private String nickname;

    // 사용자 역할
    private Role role;

    // 사용자에게 부여할 권한 목록 반환 (여러 권한을 부여 시, 수정)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    // 사용자 비밀번호 반환 (현재 소셜로그인으로 인해 비밀번호가 존재하지 않음)
    @Override
    public String getPassword() {
        return "Password";
    }

    // 사용자 아이디 반환
    @Override
    public String getUsername() {
        return this.username;
    }

    // 사용자 계정이 만료되지 않았는지 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정이 잠기지 않았는지 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 인증 정보가 만료되지 않았는지 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 상태 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    // User Entity를 CustomUserDetails로 변환
    public static CustomUserDetails fromUser(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .role(user.getRole())
                .build();
    }

    // CustomUserDetail 객체를 User Entity로 변환
    public User newEntity() {
        return new User(username, nickname, email, provider, role);
    }
}
