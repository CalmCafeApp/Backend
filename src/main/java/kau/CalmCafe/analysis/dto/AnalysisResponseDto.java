package kau.CalmCafe.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import kau.CalmCafe.promotion.domain.PromotionType;
import kau.CalmCafe.promotion.dto.PromotionUsedState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AnalysisResponseDto {
    @Schema(description = "AnalysisAboutVisitResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnalysisAboutVisitResDto {
        @Schema(description = "카페 방문하는 연령대 이미지")
        private String ageImageUrl;

        @Schema(description = "카페 방문별 성별 분포 이미지")
        private String genderImageUrl;
    }

    @Schema(description = "AnalysisAboutFavoriteResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnalysisAboutFavoriteResDto {
        @Schema(description = "주변 즐겨찾기 카페의 연령대 분포 이미지")
        private String ageDistributionImageUrl;

        @Schema(description = "주변 즐겨찾기 카페의 성별 분포 이미지")
        private String genderDistributionImageUrl;
    }

    @Schema(description = "AnalysisAboutCongestionResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnalysisAboutCongestionResDto {
        @Schema(description = "cctv 파일을 통해 관측한 요일별 평균 혼잡도 이미지")
        private String averageCongestionImageUrl;

        @Schema(description = "cctv 파일을 통해 요일별 붐비는 시간과 한산한 시간 데이터 이미지")
        private String busiestAndLeastBusyImageUrl;
    }
}
