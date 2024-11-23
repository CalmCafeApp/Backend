package kau.CalmCafe.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kau.CalmCafe.congestion.domain.CongestionLevel;
import kau.CalmCafe.promotion.dto.PromotionResponseDto.PromotionDetailResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import kau.CalmCafe.store.dto.MenuResponseDto.MenuDetailResDto;
import kau.CalmCafe.store.dto.MenuResponseDto.PointMenuDetailResDto;

import java.time.LocalTime;
import java.util.List;

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

        @Schema(description = "매장 이미지")
        private String image;

        @Schema(description = "사용자-매장 거리 (m)")
        private Integer distance;

        @Schema(description = "사용자 보유 포인트")
        private Integer point;

        @Schema(description = "즐겨찾기 수")
        private Integer favoriteCount;

        @Schema(description = "즐겨찾기 여부")
        private Boolean isFavorite;

        @Schema(description = "운영 시작 시간")
        private LocalTime openingTime;

        @Schema(description = "운영 종료 시간")
        private LocalTime closingTime;

        @Schema(description = "라스트 오더 시간")
        private LocalTime lastOrderTime;

        @Schema(description = "현재 운영 상태")
        private StoreState storeState;

        @Schema(description = "매장 측 혼잡도")
        private CongestionLevel storeCongestionLevel;

        @Schema(description = "사용자 측 혼잡도")
        private CongestionLevel userCongestionLevel;

        @Schema(description = "혼잡도 큰 차이 여부")
        private Boolean isCongestionMismatch;

        @Schema(description = "메뉴 상세 리스트")
        private List<MenuDetailResDto> menuDetailResDtoList;

        @Schema(description = "추천 카페 리스트")
        private List<RecommendStoreResDto> recommendStoreResDtoList;

        @Schema(description = "포인트 상점 판매 메뉴 리스트")
        private List<PointMenuDetailResDto> pointMenuDetailResDtoList;

        @Schema(description = "진행 중인 프로모션 리스트")
        private List<PromotionDetailResDto> promotionDetailResDtoList;

    }

    @Schema(description = "RecommendStoreResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecommendStoreResDto {

        @Schema(description = "매장 id")
        private Long id;

        @Schema(description = "매장 이름")
        private String name;

        @Schema(description = "매장 측 혼잡도")
        private CongestionLevel storeCongestionLevel;

        @Schema(description = "사용자 측 혼잡도")
        private CongestionLevel userCongestionLevel;

        @Schema(description = "매장 주소")
        private String address;

        @Schema(description = "매장 이미지")
        private String image;

    }

    @Schema(description = "StoreDetailFromCafeDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreDetailFromCafeDto {

        @Schema(description = "매장 id")
        private Long storeId;

        @Schema(description = "매장 아름")
        private String storeName;

        @Schema(description = "매장 측 혼잡도")
        private CongestionLevel storeCongestionLevel;

        @Schema(description = "유저 측 혼잡도")
        private CongestionLevel userCongestionLevel;

        @Schema(description = "매장 주소")
        private String address;

        @Schema(description = "매장 이미지")
        private String image;

        @Schema(description = "매장 개장 시간")
        private LocalTime openingTime;

        @Schema(description = "매장 폐장 시간")
        private LocalTime closingTime;

        @Schema(description = "매장 최대 수용 인원")
        private Integer maxCustomerCount;

        @Schema(description = "매장 마지막 주문 시간")
        private LocalTime lastOrderTime;

    }

    @Schema(description = "StoreCongestionFromUserDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreCongestionFromUserDto {

        @Schema(description = "매장 측 혼잡도")
        private CongestionLevel storeCongestionLevel;

        @Schema(description = "사용자 측 혼잡도")
        private CongestionLevel userCongestionLevel;

    }

    @Schema(description = "StorePosDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StorePosDto {

        @Schema(description = "매장 id")
        private Long id;

        @Schema(description = "매장 위도")
        private Double latitude;

        @Schema(description = "매장 경도")
        private Double longitude;

        @Schema(description = "매장 주소")
        private String address;

        @Schema(description = "매장 이름")
        private String name;

    }

    @Schema(description = "StorePosListDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StorePosListDto {

        @Schema(description = "주변 매장 리스트")
        private List<StorePosDto> storePosDtoList;

    }

    @Schema(description = "FavoriteStoreDetailDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FavoriteStoreDetailResDto {

        @Schema(description = "매장 id")
        private Long id;

        @Schema(description = "매장 이름")
        private String name;

        @Schema(description = "매장 측 혼잡도 등급")
        private CongestionLevel storeCongestionLevel;

        @Schema(description = "사용자 측 혼잡도 등급")
        private CongestionLevel userCongestionLevel;

    }

    @Schema(description = "StorePosListDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FavoriteStoreDetailListResDto {

        @Schema(description = "주변 매장 리스트")
        private List<FavoriteStoreDetailResDto> favoriteStoreDetailResDtoList;

    }

    @Schema(description = "SearchStoreResDto")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SearchStoreResDto {

        @Schema(description = "매장 이름")
        private String name;

        @Schema(description = "매장 id")
        private Long id;

        @Schema(description = "매장 이미지")
        private String image;

        @Schema(description = "매장 주소")
        private String address;
    }

    @Schema(description = "StoreMenuResponseDto")
    @Getter
    @AllArgsConstructor
    @Builder
    public static class StoreMenuResponseDto {

        @Schema(description = "매장 ID")
        private Long storeId;

        @Schema(description = "매장 메뉴 목록")
        private List<MenuInfo> menus;

        @Getter
        @AllArgsConstructor
        @Builder
        public static class MenuInfo {
            @Schema(description = "메뉴 ID")
            private Long id;

            @Schema(description = "메뉴 이름")
            private String name;

            @Schema(description = "메뉴 가격")
            private Integer price;

            @Schema(description = "메뉴 이미지 URL")
            private String image;
        }
    }
}
