package kau.CalmCafe.user.dto;

import kau.CalmCafe.user.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class JwtDto {
    private String accessToken;
    private String refreshToken;
    private String signIn;
    private Role role;

    public JwtDto(String accessToken, String refreshToken, String signIn, Role role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.signIn = signIn;
        this.role = role;
    }
}
