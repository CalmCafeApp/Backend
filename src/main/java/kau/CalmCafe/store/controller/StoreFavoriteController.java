package kau.CalmCafe.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.store.service.StoreFavoriteService;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매장 즐겨찾기", description = "매장 즐겨찾기 관련 api입니다.")
@RestController
@RequestMapping("/store/{store-id}/favorite")
@RequiredArgsConstructor
public class StoreFavoriteController {
    private final UserService userService;
    private final StoreFavoriteService storeFavoriteService;

    @Operation(summary = "매장 즐겨찾기 메서드", description = "매장 즐겨찾기하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAVORITE_2001", description = "매장 즐겨찾기가 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Integer> createFavorite(
            @PathVariable(name = "store-id") Long storeId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Integer favoriteCount = storeFavoriteService.createFavoriteAndRetrieveCount(storeId, user);

        return ApiResponse.onSuccess(SuccessCode.STORE_FAVORITE_SUCCESS, favoriteCount);
    }

    @Operation(summary = "매장 즐겨찾기 취소 메서드", description = "매장 즐겨찾기를 취소하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAVORITE_2003", description = "매장 즐겨찾기 취소가 완료되었습니다.")
    })
    @DeleteMapping("/delete")
    public ApiResponse<Integer> deleteFavorite(
            @PathVariable(name = "store-id") Long storeId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Integer favoriteCount = storeFavoriteService.deleteFavoriteAndRetrieveCount(storeId, user);

        return ApiResponse.onSuccess(SuccessCode.STORE_UNFAVORITE_SUCCESS, favoriteCount);
    }
}
