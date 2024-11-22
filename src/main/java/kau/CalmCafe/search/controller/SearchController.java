package kau.CalmCafe.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.search.dto.SearchResponseDto.HomeSearchResDto;
import kau.CalmCafe.search.service.SearchService;
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

@Tag(name = "검색", description = "검색 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;
    private final UserService userService;

    @Operation(summary = "지역 & 매장 이름읕 통한 검색 결과 조회", description = "검색 키워드를 주소에 포함한 매장 리스트를 반환하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SEARCH_2001", description = "홈 화면 검색 결과 반환이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "userLatitude", description = "사용자 화면 위도"),
            @Parameter(name = "userLongitude", description = "사용자 화면 경도")
    })
    @GetMapping(value = "/home")
    public ApiResponse<HomeSearchResDto> getHomeSearchResult(
            @RequestParam(name = "userLatitude") Double userLatitude,
            @RequestParam(name = "userLongitude") Double userLongitude,
            @RequestParam(name = "query") String query,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findByUserName(customUserDetails.getUsername());
        searchService.saveResearchLog(query, user);

        HomeSearchResDto homeSearchResDto = searchService.getHomeSearchResDto(query, userLatitude, userLongitude);

        return ApiResponse.onSuccess(SuccessCode.SEARCH_HOME_SUCCESS, homeSearchResDto);
    }

}
