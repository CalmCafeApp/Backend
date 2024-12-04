package kau.CalmCafe.analysis.converter;

import io.swagger.v3.oas.annotations.media.Schema;
import kau.CalmCafe.analysis.dto.AnalysisResponseDto.AnalysisAboutCongestionResDto;
import kau.CalmCafe.analysis.dto.AnalysisResponseDto.AnalysisAboutFavoriteResDto;
import kau.CalmCafe.analysis.dto.AnalysisResponseDto.AnalysisAboutVisitResDto;

public class AnalysisConverter {
    private static final String AGE_IMAGE_URL = "http://3.36.174.88:5001/static/age_distribution_target_store.png";
    private static final String GENDER_IMAGE_URL = "http://3.36.174.88:5001/static/gender_distribution_target_store.png";
    private static final String FAVORITE_MENU_DISTRIBUTION = "http://3.36.174.88:5001/static/favorite_menu_distribution_target_store.png";
    private static final String AGE_DISTRIBUTION_IMAGE_URL = "http://3.36.174.88:5001/static/age_distribution.png";
    private static final String GENDER_DISTRIBUTION_IMAGE_URL = "http://3.36.174.88:5001/static/gender_distribution.png?cache_buster=12345";
    private static final String AVERAGE_CONGESTION_IMAGE_URL = "http://3.36.174.88:5001/static/average_congestion.png";
    private static final String BUSIEST_AND_LEAST_BUSY_IMAGE_URL = "http://3.36.174.88:5001/static/busiest_and_least_busy.png";

    public static AnalysisAboutVisitResDto analysisAboutVisitResDto() {
        return AnalysisAboutVisitResDto.builder()
                .ageImageUrl(AGE_IMAGE_URL)
                .genderImageUrl(GENDER_IMAGE_URL)
                .favoriteMenuDistributionImageUrl(FAVORITE_MENU_DISTRIBUTION)
                .build();
    }

    public static AnalysisAboutFavoriteResDto analysisAboutFavoriteResDto() {
        return AnalysisAboutFavoriteResDto.builder()
                .ageDistributionImageUrl(AGE_DISTRIBUTION_IMAGE_URL)
                .genderDistributionImageUrl(GENDER_DISTRIBUTION_IMAGE_URL)
                .build();
    }

    public static AnalysisAboutCongestionResDto analysisAboutCongestionResDto() {
        return AnalysisAboutCongestionResDto.builder()
                .averageCongestionImageUrl(AVERAGE_CONGESTION_IMAGE_URL)
                .busiestAndLeastBusyImageUrl(BUSIEST_AND_LEAST_BUSY_IMAGE_URL)
                .build();
    }
}
