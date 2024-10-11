package kau.CalmCafe.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.Congestion.domain.CongestionLevel;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.store.converter.StoreConverter;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreCongestionFromUserDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailFromCafeDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailResDto;
import kau.CalmCafe.store.service.StoreService;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매장", description = "매장 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @Operation(summary = "유저 측 매장 상세 정보 조회", description = "유저 측 화면에서 매장의 상세 정보를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2001", description = "유저 측 화면에서 매장 상세 정보 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "address", description = "매장 주소"),
            @Parameter(name = "userLatitude", description = "사용자 위도"),
            @Parameter(name = "userLongitude", description = "사용자 경도")
    })
    @GetMapping(value = "/detail/user")
    public ApiResponse<StoreDetailResDto> getStoreDetailFromUSer(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "userLatitude") Double userLatitude,
            @RequestParam(name = "userLongitude") Double userLongitude
    ){
        Store store = storeService.findByAddress(address);

        Integer distance = storeService.calDistance(userLatitude, userLongitude, store.getLatitude(), store.getLongitude());

        return ApiResponse.onSuccess(SuccessCode.STORE_DETAIL_FROM_USER_SUCCESS, StoreConverter.storeDetailResDto(store, distance));
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

}
