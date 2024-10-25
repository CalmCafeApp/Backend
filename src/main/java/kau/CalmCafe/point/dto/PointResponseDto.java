package kau.CalmCafe.point.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PointResponseDto {

    @Schema(description = "PointCouponResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PointCouponResDto {

        @Schema(description = "포인트 쿠폰 id")
        private Long id;

        @Schema(description = "매장 이름")
        private String storeName;

        @Schema(description = "메뉴 이름")
        private String menuName;

        @Schema(description = "유효 기간 시작일")
        private LocalDate expirationStart;

        @Schema(description = "유효 기간 종료일")
        private LocalDate expirationEnd;

        @Schema(description = "할인율")
        private Integer discount;

    }

    @Schema(description = "PointCouponResListDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PointCouponResListDto {

        @Schema(description = "포인트 쿠폰 리스트")
        private List<PointCouponResDto> pointCouponResDtoList;

    }

}
