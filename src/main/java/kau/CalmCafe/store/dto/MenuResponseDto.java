package kau.CalmCafe.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MenuResponseDto {
    @Schema(description = "MenuDetailResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuDetailResDto {
        @Schema(description = "메뉴 id")
        private Long id;

        @Schema(description = "메뉴 이름")
        private String name;

        @Schema(description = "메뉴 가격")
        private Integer price;

        @Schema(description = "메뉴 이미지")
        private String image;
    }
}
