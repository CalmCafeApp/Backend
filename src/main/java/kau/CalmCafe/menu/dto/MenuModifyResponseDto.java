package kau.CalmCafe.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "MenuModifyResponseDto")
@Getter
@AllArgsConstructor
@Builder
public class MenuModifyResponseDto {

    @Schema(description = "메뉴 id")
    private Long id;

    @Schema(description = "메뉴 이름")
    private String name;

    @Schema(description = "메뉴 이미지")
    private String image;

    @Schema(description = "메뉴 가격")
    private Integer price;

    @Schema(description = "메뉴 포인트 가격")
    private Integer pointPrice;
}