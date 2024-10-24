package kau.CalmCafe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kau.CalmCafe.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRequestDto {

    @Schema(description = "UserReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReqDto {

        @Schema(description = "이메일")
        private String email;

        @Schema(description = "id(username)")
        private String username;

        @Schema(description = "소셜 로그인 종류")
        private String provider;

    }

    @Schema(description = "UserNicknameReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserNicknameReqDto {

        @Schema(description = "nickname")
        private String nickname;

    }

    @Schema(description = "UserSurveyInfo")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSurveyInfo {

        @Schema(description = "나이")
        private Integer age;

        @Schema(description = "성별")
        private String sex;

        @Schema(description = "직업")
        private String job;

        @Schema(description = "거주 지역")
        private String residence;

        @Schema(description = "결혼 여부")
        private String marriage;

    }

}
