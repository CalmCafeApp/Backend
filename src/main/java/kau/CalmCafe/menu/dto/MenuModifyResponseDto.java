package kau.CalmCafe.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "MenuDeleteResponseDto")
@Getter
@AllArgsConstructor
@Builder
public class MenuModifyResponseDto {

    @Schema(description = "메뉴 id")
    private Long id;

    @Schema(description = "메뉴 이름")
    private String name;
}