package kau.CalmCafe.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.store.converter.StoreConverter;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.StorePosListDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreCongestionFromUserDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailFromCafeDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailResDto;
import kau.CalmCafe.store.service.MenuService;
import kau.CalmCafe.point.service.PointCouponService;
import kau.CalmCafe.store.service.StoreService;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매장", description = "매장 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;
    private final MenuService menuService;
    private final PointCouponService pointCouponService;

    @Operation(summary = "유저 측 매장 상세 정보 조회", description = "유저 측 화면에서 매장의 상세 정보를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2001", description = "유저 측 화면에서 매장 상세 정보 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "매장 id"),
            @Parameter(name = "userLatitude", description = "사용자 위도"),
            @Parameter(name = "userLongitude", description = "사용자 경도")
    })
    @GetMapping(value = "/detail/user")
    public ApiResponse<StoreDetailResDto> getStoreDetailFromUSer(
            @RequestParam(name = "storeId") Long storeId,
            @RequestParam(name = "userLatitude") Double userLatitude,
            @RequestParam(name = "userLongitude") Double userLongitude,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findByUserName(customUserDetails.getUsername());

        Store store = storeService.findById(storeId);

        Integer distance = storeService.calDistance(userLatitude, userLongitude, store.getLatitude(), store.getLongitude());

        List<Menu> menuList = menuService.findMenuListByStore(store);

        List<Store> recommendStoreList = storeService.getRecommendStoreList(store);

        List<Menu> pointMenuList = menuService.getPointMenuList(store);

        return ApiResponse.onSuccess(SuccessCode.STORE_DETAIL_FROM_USER_SUCCESS, StoreConverter.storeDetailResDto(store, distance, menuList, recommendStoreList, pointMenuList, user));
    }

    @Operation(summary = "카페 측 매장 상세 정보 조회", description = "카페 측 화면에서 상세 정보를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2002", description = "카페 측 화면에서 매장 상세 정보 조회가 완료되었습니다.")
    })
    @GetMapping("/detail/cafe")
    public ApiResponse<StoreDetailFromCafeDto> getStoreDetailFromCafe(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        Store store = user.getStore();

        return ApiResponse.onSuccess(SuccessCode.STORE_DETAIL_FROM_CAFE_SUCCESS, StoreConverter.storeDetailFromCafeDto(store));
    }

    @Operation(summary = "유저 측 매장 혼잡도 조회", description = "유저 측 화면에서 특정 매장의 혼잡도를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2005", description = "유저 측 화면에서 매장 혼잡도 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "매장 id"),
    })
    @GetMapping("/detail/user/congestion")
    public ApiResponse<StoreCongestionFromUserDto> getStoreCongestionFromUser(
            @RequestParam(name = "storeId") Long storeId
    ) {
        Store store = storeService.findById(storeId);

        return ApiResponse.onSuccess(SuccessCode.STORE_CONGESTION_GET_SUCCESS, StoreConverter.storeCongestionFromUserDto(store));
    }

    @Operation(summary = "사용자 화면 주소를 통한 주변 매장 좌표 조회", description = "사용자 화면 주소를 통해 주변 매장 좌표를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2006", description = "주변 매장 좌표 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "userAddress", description = "사용자 화면의 주소 정보")
    })
    @GetMapping("/")
    public ApiResponse<StorePosListDto> getNearStorePosList(
            @RequestParam(name = "userAddress") String userAddress
    ) {
        List<Store> storeList = storeService.getNearStoreList(userAddress);

        return ApiResponse.onSuccess(SuccessCode.STORE_NEAR_LIST_SUCCESS, StoreConverter.storePosListDto(storeList));
    }

    @Operation(summary = "매장 영업 시간 수정", description = "사장님이 매장의 영업 시간을 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2011", description = "매장 영업 시간 수정이 완료되었습니다.")
    })
    @PatchMapping("/modify/hours")
    public ApiResponse<StoreDetailFromCafeDto> updateStoreHours(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String openingTime,
            @RequestParam String closingTime
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        Store updatedStore = storeService.updateStoreHours(user.getStore(), openingTime, closingTime);

        return ApiResponse.onSuccess(SuccessCode.STORE_TIME_UPDATE_SUCCESS, StoreConverter.storeDetailFromCafeDto(updatedStore));
    }

    @Operation(summary = "마지막 주문 시간 수정", description = "사장님이 매장의 마지막 주문 시간을 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2012", description = "매장 영업 시간 수정이 완료되었습니다.")
    })
    @PatchMapping("/modify/lastordertime")
    public ApiResponse<StoreDetailFromCafeDto> updateLastOrderTime(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String lastOrderTime
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        Store updatedStore = storeService.updateLastOrderTime(user.getStore(), lastOrderTime);

        return ApiResponse.onSuccess(SuccessCode.STORE_LAST_ORDER_TIME_UPDATE_SUCCESS, StoreConverter.storeDetailFromCafeDto(updatedStore));
    }

    @Operation(summary = "매장 최대 수용 인원 수정", description = "사장님이 매장의 최대 수용 인원을 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2013", description = "매장 최대 수용 인원 수정이 완료되었습니다.")
    })
    @PatchMapping("/modify/max-capacity")
    public ApiResponse<StoreDetailFromCafeDto> updateMaxCapacity(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer maxCapacity
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        Store updatedStore = storeService.updateMaxCapacity(user.getStore(), maxCapacity);

        return ApiResponse.onSuccess(SuccessCode.STORE_CAPACITY_UPDATE_SUCCESS, StoreConverter.storeDetailFromCafeDto(updatedStore));
    }


}

