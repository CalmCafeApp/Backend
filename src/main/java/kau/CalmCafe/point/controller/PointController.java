package kau.CalmCafe.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.point.converter.PointConverter;
import kau.CalmCafe.point.dto.PointResponseDto.PointCouponResListDto;
import kau.CalmCafe.point.service.PointService;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.point.domain.PointCoupon;
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
    private final PointService pointService;

    @Operation(summary = "포인트 스토어 내 상품 구매", description = "포인트 스토어 내 상품을 구매합니다. (잔여 포인트 반환)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POINT_2001", description = "포인트 스토어 내 상품 구매가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "menuId", description = "메뉴 id")
    })
    @GetMapping("/buy")
    public ApiResponse<Integer> buyCPointCoupon(
            @RequestParam(name = "menuId") Long menuId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Menu menu = menuService.findById(menuId);

        pointService.createPointCoupon(user, menu);

        return ApiResponse.onSuccess(SuccessCode.BUY_POINT_COUPON_SUCCESS, user.getPoint());
    }

    @Operation(summary = "사용자 보유 포인트 쿠폰 리스트 반환", description = "사용자가 보유한 포인트 쿠폰 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POINT_2002", description = "사용자가 보유한 포인트 쿠폰 리스트가 완료되었습니다.")
    })
    @GetMapping("/list")
    public ApiResponse<PointCouponResListDto> getMyPointCouponList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<PointCoupon> pointCouponList = pointService.getMyPointCouponList(user);

        return ApiResponse.onSuccess(SuccessCode.MY_POINT_COUPON_LIST_SUCCESS, PointConverter.pointCouponResListDto(pointCouponList));
    }

}
