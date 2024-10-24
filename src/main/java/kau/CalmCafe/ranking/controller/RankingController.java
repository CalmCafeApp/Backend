package kau.CalmCafe.ranking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.ranking.converter.RankingConverter;
import kau.CalmCafe.ranking.dto.RankingResponseDto.RankingListResDto;
import kau.CalmCafe.ranking.service.RankingService;
import kau.CalmCafe.store.domain.Store;
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

@Tag(name = "랭킹", description = "랭킹 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final UserService userService;
    private final RankingService rankingService;

    @Operation(summary = "실시간 방문자 수 TOP 100 매장 반환", description = "실시간 방문자 수 TOP 100 매장 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "RANKING_2001", description = "실시간 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "location", description = "지역 문자열 (전국, 서울, 경기, 인천, 제주, 부산, 대구, 광주, 대전, 울산, 경상, 전라, 강원, 충청, 세종)")
    })
    @GetMapping("/congestion")
    public ApiResponse<RankingListResDto> getRankingByCongestion(
            @RequestParam(name = "location") String location,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<Store> rankingStoreList = rankingService.getRankingStoreListByCongestion(location);

        return ApiResponse.onSuccess(SuccessCode.RANKING_CONGESTION_SUCCESS, RankingConverter.rankingListResDto(rankingStoreList, user));
    }

    @Operation(summary = "누적 방문자 수 TOP 100 매장 반환", description = "누적 방문자 수 TOP 100 매장 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "RANKING_2002", description = "누적 방문자 수 TOP 100 매장 리스트 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "location", description = "지역 문자열 (전국, 서울, 경기, 인천, 제주, 부산, 대구, 광주, 대전, 울산, 경상, 전라, 강원, 충청, 세종)")
    })
    @GetMapping("/total")
    public ApiResponse<RankingListResDto> getRankingByTotalVisit(
            @RequestParam(name = "location") String location,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<Store> rankingStoreList = rankingService.getRankingStoreListByTotalVisit(location);

        return ApiResponse.onSuccess(SuccessCode.RANKING_TOTAL_VISIT_SUCCESS, RankingConverter.rankingListResDto(rankingStoreList, user));
    }

    @Operation(summary = "즐겨찾기 수 TOP 100 매장 반환", description = "즐겨찾기 수 TOP 100 매장 리스트를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "RANKING_2003", description = "즐겨찾기 수 TOP 100 매장 리스트 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "location", description = "지역 문자열 (전국, 서울, 경기, 인천, 제주, 부산, 대구, 광주, 대전, 울산, 경상, 전라, 강원, 충청, 세종)")
    })
    @GetMapping("/favorite")
    public ApiResponse<RankingListResDto> getRankingByFavorite(
            @RequestParam(name = "location") String location,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        List<Store> rankingStoreList = rankingService.getRankingStoreListByFavorite(location);

        return ApiResponse.onSuccess(SuccessCode.RANKING_FAVORITE_SUCCESS, RankingConverter.rankingListResDto(rankingStoreList, user));
    }

}
