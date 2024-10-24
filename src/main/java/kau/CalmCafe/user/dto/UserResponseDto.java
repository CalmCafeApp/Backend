package kau.CalmCafe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserResponseDto {

    @Schema(description = "UserProfileResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfileResDto {

        @Schema(name = "유저 닉네임")
        private String nickname;

        @Schema(name = "포인트 수")
        private Integer point;

        @Schema(name = "프로필 이미지")
        private String profileImage;

    }
}
