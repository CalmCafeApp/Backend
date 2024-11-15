package kau.CalmCafe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kau.CalmCafe.user.domain.Role;
import kau.CalmCafe.user.domain.User;
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
        @Schema(description = "성별")
        @NotBlank(message = "성별은 필수 입력값입니다.")
        private String gender;

        @NotBlank(message = "나이은 필수 입력값입니다.")
        @Schema(description = "나이")
        private String age;

        @NotBlank(message = "나이은 필수 입력값입니다.")
        @Schema(description = "직업")
        private String job;

        @Schema(description = "거주 지역")
        private String location;

        @Schema(description = "결혼 여부")
        private String marriage;

        @Schema(description = "취미")
        private String hobby;

        @Schema(description = "가장 좋아하는 메뉴")
        private String favoriteMenu;

        @Schema(description = "카페 이용 목적")
        private String cafeUsingPurpose;

        @Schema(description = "카페 선택 요인")
        private String cafeChooseCause;

        @Schema(description = "카페 방문 빈도")
        private String cafeVisitedFrequency;

        @Schema(description = "SNS 사용 여부")
        private String isUsingSNS;

        @Schema(description = "편의시설 선호")
        private String convenienceFacilityPrefer;
    }
}
