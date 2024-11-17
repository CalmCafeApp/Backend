package kau.CalmCafe.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @DeleteMapping("/{menu-id}/delete")
    public ApiResponse<MenuModifyResponseDto> deleteMenu(
            @PathVariable(name = "menu-id") Long menuId
    ) {
        MenuModifyResponseDto deletedMenu = menuModifyService.deleteMenu(menuId);
        return ApiResponse.onSuccess(SuccessCode.MENU_DELETE_SUCCESS, deletedMenu);
    }

    @Operation(summary = "메뉴 수정", description = "특정 메뉴의 정보를 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MENU_2002", description = "메뉴 수정이 완료되었습니다.")
    })
    @PatchMapping("/{menu-id}/update")
    public ApiResponse<MenuModifyResponseDto> updateMenu(
            @PathVariable(name = "menu-id") Long menuId,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) Integer price
    ) {
        MenuModifyResponseDto updatedMenu = menuModifyService.updateMenu(menuId, image, price);
        return ApiResponse.onSuccess(SuccessCode.MENU_UPDATE_SUCCESS, updatedMenu);
    }

    @Operation(summary = "메뉴 등록", description = "새로운 메뉴를 등록하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MENU_2003", description = "메뉴 등록이 완료되었습니다.")
    })
    @PostMapping("/register")
    public ApiResponse<MenuModifyResponseDto> registerMenu(
            @Parameter(description = "매장 ID", required = true) @RequestParam Long storeId,
            @Parameter(description = "메뉴 이름", required = true) @RequestParam String name,
            @Parameter(description = "메뉴 가격", required = true) @RequestParam Integer price,
            @Parameter(description = "메뉴 이미지 URL") @RequestParam(required = false) String image
    ) {
        MenuModifyResponseDto registeredMenu = menuModifyService.registerMenu(storeId, name, price, image);
        return ApiResponse.onSuccess(SuccessCode.MENU_REGISTER_SUCCESS, registeredMenu);
    }
}