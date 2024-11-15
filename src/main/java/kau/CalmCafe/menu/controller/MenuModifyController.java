package kau.CalmCafe.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.menu.service.MenuModifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "메뉴", description = "메뉴 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuModifyController {

    private final MenuModifyService menuModifyService;

    @Operation(summary = "메뉴 삭제", description = "특정 메뉴를 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MENU_2001", description = "메뉴 삭제가 완료되었습니다.")
    })
    @DeleteMapping("/{menu-id}")
    public ApiResponse<MenuModifyResponseDto> deleteMenu(
            @PathVariable(name = "menu-id") Long menuId
    ) {
        MenuModifyResponseDto deletedMenu = menuModifyService.deleteMenu(menuId);
        return ApiResponse.onSuccess(SuccessCode.MENU_DELETE_SUCCESS, deletedMenu);
    }
}