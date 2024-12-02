package kau.CalmCafe.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;

import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.menu.dto.DiscountedMenuDto;
import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.menu.service.MenuModifyService;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "메뉴", description = "메뉴 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuModifyController {
    private final MenuModifyService menuModifyService;
    private final UserService userService;

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
    @PatchMapping(value = "/{menu-id}/menu-image/update", consumes = {"multipart/form-data"})
    public ApiResponse<MenuModifyResponseDto> updateMenu(
            @PathVariable(name = "menu-id") Long menuId,
            @RequestPart(value = "menuImage", required = false) MultipartFile menuImage,
            @RequestParam(required = false) Integer price
    ) throws IOException {
        MenuModifyResponseDto updatedMenu = menuModifyService.updateMenu(menuId, menuImage, price);
        return ApiResponse.onSuccess(SuccessCode.MENU_UPDATE_SUCCESS, updatedMenu);
    }

    @Operation(summary = "메뉴 등록", description = "새로운 메뉴를 등록하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MENU_2003", description = "메뉴 등록이 완료되었습니다.")
    })
    @PostMapping(value = "/menu-image/register/", consumes = {"multipart/form-data"})
    public ApiResponse<MenuModifyResponseDto> registerMenu(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String name,
            @RequestParam Integer price,
            @RequestPart(value = "menuImage") MultipartFile menuImage
    ) throws IOException {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Long storeId = user.getStore().getId();

        MenuModifyResponseDto registeredMenu = menuModifyService.registerMenu(storeId, name, price, menuImage);
        return ApiResponse.onSuccess(SuccessCode.MENU_REGISTER_SUCCESS, registeredMenu);
    }

    @Operation(summary = "포인트 메뉴 조회", description = "매장의 포인트 메뉴를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MENU_2004", description = "매장의 포인트 메뉴 조회가 완료되었습니다.")
    })
    @GetMapping("/discounted/")
    public ApiResponse<List<DiscountedMenuDto>> getDiscountedMenus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Long storeId = user.getStore().getId();

        List<DiscountedMenuDto> discountedMenus = menuModifyService.getDiscountedMenus(storeId);
        return ApiResponse.onSuccess(SuccessCode.POINT_MENU_INQUIRY_SUCCESS, discountedMenus);
    }

    @Operation(summary = "포인트 스토어 미등록 상품 조회", description = "포인트 스토어에 미등록된 상품들을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MENU_2005", description = "포인트 스토어 미등록 상품 반환이 완료되었습니다.")
    })
    @GetMapping("/non-discounted/")
    public ApiResponse<List<MenuModifyResponseDto>> getNonDiscountedMenus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Long storeId = user.getStore().getId();

        List<MenuModifyResponseDto> nonDiscountedMenus = menuModifyService.getNonDiscountedMenus(storeId);
        return ApiResponse.onSuccess(SuccessCode.UNREGISTERED_MENU_INQUIRY_SUCCESS, nonDiscountedMenus);
    }
}