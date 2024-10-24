package kau.CalmCafe.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.point.service.PointCouponService;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.PointCoupon;
import kau.CalmCafe.store.service.MenuService;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포인트", description = "포인트 스토어 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final UserService userService;
    private final MenuService menuService;
    private final PointCouponService pointCouponService;

    @Operation(summary = "포인트 스토어 내 상품 구매", description = "포인트 스토어 내 상품을 구매합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POINT_2001", description = "포인트 스토어 내 상품 구매가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "menuId", description = "메뉴 id")
    })
    @GetMapping("/buy")
    public ApiResponse<Long> buyCPointCoupon(
            @RequestParam(name = "menuId") Long menuId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Menu menu = menuService.findById(menuId);

        PointCoupon pointCoupon = pointCouponService.createPointCoupon(user, menu);

        return ApiResponse.onSuccess(SuccessCode.BUY_POINT_COUPON_SUCCESS, pointCoupon.getId());
    }

}
