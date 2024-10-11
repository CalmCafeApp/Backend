package kau.CalmCafe.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
public class StoreResponseDto {
    @Schema(description = "StoreDetailResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreDetailResDto {
        @Schema(description = "매장 id")
        private Long id;

        @Schema(description = "매장 이름")
        private String name;

        @Schema(description = "사용자-매장 거리 (m)")
        private Integer distance;

        @Schema(description = "즐겨찾기 수")
        private Integer favoriteCount;

        @Schema(description = "운영 시작 시간")
        private LocalTime openingTime;

        @Schema(description = "운영 종료 시간")
        private LocalTime closingTime;

        @Schema(description = "라스트 오더 시간")
        private LocalTime lastOrderTime;

        @Schema(description = "현재 운영 상태")
        private StoreState storeState;

        @Schema(description = "매장 측 혼잡도")
        private Integer storeCongestionValue;

        @Schema(description = "사용자 측 혼잡도")
        private Integer userCongestionValue;
    }

    @Schema(description = "StoreDetailFromCafeDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreDetailFromCafeDto {
        @Schema(description = "매장 id")
        private Long storeId;

        @Schema(description = "매장 이름")
        private String storeName;

        @Schema(description = "매장 측 혼잡도")
        private Integer storeCongestionValue;

        @Schema(description = "사용자 측 혼잡도")
        private Integer userCongestionValue;
    }

    @Schema(description = "StoreCongestionFromUserDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreCongestionFromUserDto {
        @Schema(description = "매장 측 혼잡도")
        private Integer storeCongestionValue;

        @Schema(description = "사용자 측 혼잡도")
        private Integer userCongestionValue;
    }
}
