package kau.CalmCafe.Congestion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.Congestion.domain.CongestionLevel;
import kau.CalmCafe.Congestion.service.CongestionInputService;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.service.StoreService;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "혼잡도 입력", description = "혼잡도 입력 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/congestion/input/")
public class CongestionInputController {

    private final UserService userService;
    private final CongestionInputService congestionInputService;
    private final StoreService storeService;

    @Operation(summary = "혼잡도 입력 메서드", description = "혼잡도 입력을 받는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CONGESTION_2011", description = "혼잡도 입력이 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "congestionValue", description = "혼잡도 수치")
    })
    @PostMapping("/create")
    public ApiResponse<CongestionLevel> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "storeId") Long storeId,
            @RequestParam(name = "congestionValue") Integer congestionValue
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        Store store = storeService.findById(storeId);

        CongestionLevel congestionLevel = congestionInputService.inputAndRetrieveCongestion(user, congestionValue, store);

        return ApiResponse.onSuccess(SuccessCode.CONGESTION_INPUT_SUCCESS, congestionLevel);
    }
}
