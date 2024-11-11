package kau.CalmCafe.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kau.CalmCafe.store.dto.StoreResponseDto.SearchStoreResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SearchResponseDto {
    @Schema(description = "HomeSearchResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HomeSearchResDto {
        @Schema(description = "화면 이동 위도")
        private Double latitude;

        @Schema(description = "화면 이동 경도")
        private Double longitude;

        @Schema(description = "매장 목록")
        private List<SearchStoreResDto> searchStoreResDtoList;
    }
}
