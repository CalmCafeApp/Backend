package kau.CalmCafe.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.user.converter.UserConverter;
import kau.CalmCafe.user.domain.Role;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.dto.JwtDto;
import kau.CalmCafe.user.dto.UserRequestDto.UserSurveyInfo;
import kau.CalmCafe.user.dto.UserResponseDto.UserProfileResDto;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import kau.CalmCafe.global.api_payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "로그아웃", description = "로그아웃하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2001", description = "로그아웃 되었습니다.")
    })
    @DeleteMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ApiResponse.onSuccess(SuccessCode.USER_LOGOUT_SUCCESS, "Refresh Token 삭제를 완료했습니다.");
    }

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2002", description = "토큰 재발급이 완료되었습니다.")
    })
    @PostMapping("/reissue")
    public ApiResponse<JwtDto> reissue(HttpServletRequest request) {
        JwtDto jwt = userService.reissue(request);
        return ApiResponse.onSuccess(SuccessCode.USER_REISSUE_SUCCESS, jwt);
    }

    @Operation(summary = "유저 프로필 정보 반환", description = " 유저 프로필 정보를 반환하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2003", description = "프로필 정보 반환이 완료되었습니다.")
    })
    @GetMapping("/profile")
    public ApiResponse<UserProfileResDto> getUserProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        return ApiResponse.onSuccess(SuccessCode.USER_PROFILE_GET_SUCCESS, UserConverter.userProfileResDto(user));
    }

    @Operation(summary = "설문 조사를 통한 사용자 정보 저장", description = "설문 조사를 통한 사용자 정보를 저장합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2004", description = "설문 조사를 통한 사용자 정보 저장이 완료되었습니다.")
    })
    @PostMapping("/survey")
    public ApiResponse<Long> saveUserSurveyInfo(
            @Valid @RequestBody UserSurveyInfo userSurveyInfo,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        return ApiResponse.onSuccess(SuccessCode.USER_SURVEY_SUCCESS, userService.saveSurveyInfo(user, userSurveyInfo));
    }

    @Operation(summary = "유저 역할 변경", description = "유저의 현재 역할을 변경하는 메서드입니다. (User -> Cafe 또는 Cafe -> User)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2005", description = "유저 역할 변경이 완료되었습니다.")
    })
    @GetMapping("/role")
    public ApiResponse<Role> changeUserRole(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        return ApiResponse.onSuccess(SuccessCode.USER_CHANGE_ROLE_SUCCESS, userService.changeRole(user));
    }
}
