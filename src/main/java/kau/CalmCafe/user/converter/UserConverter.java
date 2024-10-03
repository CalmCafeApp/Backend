package kau.CalmCafe.user.converter;

import kau.CalmCafe.user.domain.Role;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.dto.JwtDto;
import kau.CalmCafe.user.dto.UserRequestDto.UserReqDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserConverter {
    public static User saveUser(UserReqDto userReqDto, String nick) {

        return User.builder()
                .email(userReqDto.getEmail())
                .username(userReqDto.getUsername())
                .provider(userReqDto.getProvider())
                .role(Role.USER)
                .nickname(nick)
                .build();
    }

    public static JwtDto jwtDto(String access, String refresh, String signIn, Role role) {
        return JwtDto.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .signIn(signIn)
                .role(role)
                .build();
    }
}
