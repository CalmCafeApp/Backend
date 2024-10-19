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
import kau.CalmCafe.store.domain.PointCoupon;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreRankingListResDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StorePosListDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreCongestionFromUserDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailFromCafeDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailResDto;
import kau.CalmCafe.store.service.MenuService;
import kau.CalmCafe.store.service.PointCouponService;
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

    @Operation(summary = "포인트 스토어 내 상품 구매", description = "포인트 스토어 내 상품을 구매합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2007", description = "포인트 스토어 내 상품 구매가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "menuId", description = "메뉴 id")
    })
    @GetMapping("/point/buy")
    public ApiResponse<Long> buyCPointCoupon(
            @RequestParam(name = "menuId") Long menuId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());
        Menu menu = menuService.findById(menuId);

        PointCoupon pointCoupon = pointCouponService.createPointCoupon(user, menu);

        return ApiResponse.onSuccess(SuccessCode.STORE_BUY_COUPON_POINT_SUCCESS, pointCoupon.getId());
    }

    @Operation(summary = "실시간 방문자 수 TOP 100 매장 반환", description = "실시간 방문자 수 TOP 100 매장 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2008", description = "실시간 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "location", description = "지역 문자열 (전국, 서울, 경기, 인천, 제주, 부산, 대구, 광주, 대전, 울산, 경상, 전라, 강원, 충청, 세종)")
    })
    @GetMapping("/ranking/congestion")
    public ApiResponse<StoreRankingListResDto> getRankingByCongestion(
            @RequestParam(name = "location") String location,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<Store> rankingStoreList = storeService.getRankingStoreListByCongestion(location);

        return ApiResponse.onSuccess(SuccessCode.STORE_RANKING_CONGESTION_SUCCESS, StoreConverter.storeRankingListResDto(rankingStoreList, user));
    }

    @Operation(summary = "누적 방문자 수 TOP 100 매장 반환", description = "누적 방문자 수 TOP 100 매장 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2009", description = "누적 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "location", description = "지역 문자열 (전국, 서울, 경기, 인천, 제주, 부산, 대구, 광주, 대전, 울산, 경상, 전라, 강원, 충청, 세종)")
    })
    @GetMapping("/ranking/total")
    public ApiResponse<StoreRankingListResDto> getRankingByTotalVisit(
            @RequestParam(name = "location") String location,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<Store> rankingStoreList = storeService.getRankingStoreListByTotalVisit(location);

        return ApiResponse.onSuccess(SuccessCode.STORE_RANKING_TOTAL_VISIT_SUCCESS, StoreConverter.storeRankingListResDto(rankingStoreList, user));
    }

    @Operation(summary = "즐겨찾기 수 TOP 100 매장 반환", description = "즐겨찾기 수 TOP 100 매장 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE_2010", description = "즐겨찾기 수 TOP 100 매장 리스트 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "location", description = "지역 문자열 (전국, 서울, 경기, 인천, 제주, 부산, 대구, 광주, 대전, 울산, 경상, 전라, 강원, 충청, 세종)")
    })
    @GetMapping("/ranking/favorite")
    public ApiResponse<StoreRankingListResDto> getRankingByFavorite(
            @RequestParam(name = "location") String location,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<Store> rankingStoreList = storeService.getRankingStoreListByFavorite(location);

        return ApiResponse.onSuccess(SuccessCode.STORE_RANKING_FAVORITE_SUCCESS, StoreConverter.storeRankingListResDto(rankingStoreList, user));
    }



}
