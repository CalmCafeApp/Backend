package kau.CalmCafe.promotion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau.CalmCafe.global.api_payload.ApiResponse;
import kau.CalmCafe.global.api_payload.SuccessCode;
import kau.CalmCafe.promotion.dto.PromotionResponseDto;
import kau.CalmCafe.promotion.dto.PromotionResponseDto.PromotionDetailResDto;
import kau.CalmCafe.promotion.service.PromotionService;
import kau.CalmCafe.promotion.service.PromotionUsedService;
import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.user.domain.User;
import kau.CalmCafe.user.jwt.CustomUserDetails;
import kau.CalmCafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "프로모션", description = "프로모션 관련 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {

    private final UserService userService;
    private final PromotionService promotionService;
    private final PromotionUsedService promotionUsedService;

    @Operation(summary = "프로모션 이용", description = "사용자가 특정 매장의 프로모션을 이용하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PROMOTION_2001", description = "사용자의 특정 매장의 프로모션 이용이 완료되었습니다.")
    })
    @GetMapping("/use/{promotion-id}")
    public ApiResponse<Long> usePromotion(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable(name = "promotion-id") Long promotionId
    ) {
        User user = userService.findByUserName(customUserDetails.getUsername());

        Promotion promotion = promotionService.findById(promotionId);

        return ApiResponse.onSuccess(SuccessCode.PROMOTION_USE_SUCCESS, promotionUsedService.createPromotionUsed(user, promotion));
    }

    @Operation(summary = "프로모션 할인율 수정", description = "특정 프로모션의 할인율을 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PROMOTION_2002", description = "프로모션 할인율 수정이 완료되었습니다.")
    })
    @PatchMapping("/modify/discount")
    public ApiResponse<PromotionResponseDto.PromotionDetailResDto> updatePromotionDiscount(
            @RequestParam Long promotionId,
            @RequestParam Integer discount
    ) {
        PromotionDetailResDto updatedPromotion = promotionService.updatePromotionDiscount(promotionId, discount);
        return ApiResponse.onSuccess(SuccessCode.PROMOTION_UPDATE_SUCCESS, updatedPromotion);
    }

    @Operation(summary = "프로모션 사용기간 수정", description = "특정 프로모션의 사용기간을 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PROMOTION_2003", description = "프로모션 사용기간 수정이 완료되었습니다.")
    })
    @PatchMapping("/modify/period")
    public ApiResponse<PromotionResponseDto.PromotionDetailResDto> updatePromotionPeriod(
            @RequestParam Long promotionId,
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        PromotionDetailResDto updatedPromotion = promotionService.updatePromotionPeriod(promotionId, startTime, endTime);
        return ApiResponse.onSuccess(SuccessCode.PROMOTION_PERIOD_UPDATE_SUCCESS, updatedPromotion);
    }

    @Operation(summary = "매장의 프로모션 조회", description = "매장의 프로모션을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PROMOTION_2004", description = "매장의 프로모션 조회가 완료되었습니다.")
    })
    @GetMapping("/store/{store-id}")
    public ApiResponse<List<PromotionResponseDto.PromotionDetailResDto>> getStorePromotions(
            @PathVariable(name = "store-id") Long storeId
    ) {
        List<PromotionDetailResDto> promotions = promotionService.getPromotionsByStoreId(storeId);
        return ApiResponse.onSuccess(SuccessCode.PROMOTION_RETRIEVE_SUCCESS, promotions);
    }
}
