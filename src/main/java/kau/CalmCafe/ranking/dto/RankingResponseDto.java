package kau.CalmCafe.ranking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kau.CalmCafe.congestion.domain.CongestionLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RankingResponseDto {

    @Schema(description = "RankingResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RankingResDto {

        @Schema(description = "매장 id")
        private Long id;

        @Schema(description = "매장 이름")
        private String name;

        @Schema(description = "매장 측 혼잡도")
        private CongestionLevel storeCongestionLevel;

        @Schema(description = "사용자 측 혼잡도")
        private CongestionLevel userCongestionLevel;

        @Schema(description = "즐겨찾기 여부")
        private Boolean isFavorite;

        @Schema(description = "매장 이미지")
        private String image;

        @Schema(description = "매장 주소")
        private String address;

    }

    @Schema(description = "RankingListResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RankingListResDto {

        @Schema(description = "주변 매장 리스트")
        private List<RankingResDto> StoreRankingResDtoList;

    }
}
