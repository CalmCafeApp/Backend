package kau.CalmCafe.user.controller;

import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.user.converter.UserConverter;
import kau.CalmCafe.user.domain.Role;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.dto.JwtDto;
import kau.CalmCafe.user.dto.UserRequestDto.UserReqDto;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰", description = "access token 관련 api입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final UserService userService;

    @Operation(summary = "토큰 반환", description = "Front-end 로부터 유저 정보를 받아 토큰을 반환하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2011", description = "회원가입 & 로그인 성공")
    })
    @PostMapping("/generate")
    public ApiResponse<JwtDto> tokenToFront(
            @RequestBody UserReqDto userReqDto
    ) {
        // 이메일을 통한 회원 가입 여부 판단
        Boolean isMember = userService.checkMemberByEmail(userReqDto.getEmail());

        String accessToken = "";
        String refreshToken = "";

        String signIn = "wasUser";

        // 기존 회원인 경우
        if(isMember) {
            // JWT 생성 (재생성)
            JwtDto jwtDto = userService.jwtMakeSave(userReqDto.getUsername());

            // 변수에 저장
            accessToken = jwtDto.getAccessToken();
            refreshToken = jwtDto.getRefreshToken();
        }
        // 신규 회원인 경우
        else {
            // DB 정보 사용자 정보 생성 (회원 가입)
            User user = userService.createUser(userReqDto);

            // jwt 생성 (새로 생성)
            JwtDto jwtDto = userService.jwtMakeSave(userReqDto.getUsername());

            // 변수에 저장
            accessToken = jwtDto.getAccessToken();
            refreshToken = jwtDto.getRefreshToken();

            signIn = "newUser";
        }

        Role role = userService.getRole(userReqDto.getEmail());

        return ApiResponse.onSuccess(SuccessCode.USER_LOGIN_SUCCESS, UserConverter.jwtDto(accessToken, refreshToken, signIn, role));
    }
}
