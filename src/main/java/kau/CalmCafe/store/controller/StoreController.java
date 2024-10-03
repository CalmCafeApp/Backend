package kau.CalmCafe.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.store.converter.StoreConverter;
import kau.CalmCafe.store.domain.Store;
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

    @Operation(summary = "매장 상세 정보 메서드", description = "매장의 상세 정보를 반환하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2001", description = "매장 상세 정보 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "address", description = "매장 주소"),
            @Parameter(name = "userLatitude", description = "사용자 위도"),
            @Parameter(name = "userLongitude", description = "사용자 경도")
    })
    @GetMapping(value = "/detail")
    public ApiResponse<StoreDetailResDto> storeDetail(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "userLatitude") Double userLatitude,
            @RequestParam(name = "userLongitude") Double userLongitude
    ){
        Store store = storeService.findByAddress(address);

        Integer distance = storeService.calDistance(userLatitude, userLongitude, store.getLatitude(), store.getLongitude());

        return ApiResponse.onSuccess(SuccessCode.STORE_DETAIL_SUCCESS, StoreConverter.storeDetailResDto(store, distance));
    }
}
