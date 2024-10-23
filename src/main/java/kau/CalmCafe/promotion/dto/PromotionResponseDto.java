package kau.CalmCafe.promotion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import kau.CalmCafe.promotion.domain.PromotionType;
import kau.CalmCafe.store.dto.PromotionUsedState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PromotionResponseDto {

    @Schema(description = "PromotionFromUserResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PromotionDetailResDto {

        @Schema(description = "프로모션 id")
        private Long id;

        @Schema(description = "프로모션 사용 가능 시작 시간")
        private LocalTime startTime;

        @Schema(description = "프로모션 사용 가능 종료 시간")
        private LocalTime endTime;

        @Schema(description = "프로모션 할인율")
        private Integer discount;

        @Schema(description = "프로모션 유형")
        private PromotionType promotionType;

        @Schema(description = "프로모션 이용 여부")
        private PromotionUsedState promotionUsedState;

    }

}
