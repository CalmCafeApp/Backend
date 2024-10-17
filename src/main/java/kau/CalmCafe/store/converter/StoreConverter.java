package kau.CalmCafe.store.converter;

import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.MenuResponseDto.MenuDetailResDto;
import kau.CalmCafe.store.dto.StoreResponseDto.RecommendStoreResDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StorePosListDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StorePosDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreCongestionFromUserDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailFromCafeDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailResDto;
import kau.CalmCafe.store.dto.StoreState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreConverter {

    public static StoreDetailResDto storeDetailResDto(Store store, Integer distance, List<Menu> menuList, List<Store> recommendStoreList) {

        // 현재 시간
        LocalTime now = LocalTime.now();

        // 현재 매장 상태
        StoreState storeState = (now.isAfter(store.getOpeningTime()) && now.isBefore(store.getClosingTime())) ? StoreState.OPEN : StoreState.CLOSE;

        List<MenuDetailResDto> menuDetailResDtoList = menuList.stream()
                .map(MenuConverter::menuDetailResDto)
                .toList();

        List<RecommendStoreResDto> recommendStoreResDtoList = recommendStoreList.stream()
                .map(StoreConverter::recommendStoreResDto)
                .toList();

        return StoreDetailResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .distance(distance)
                .favoriteCount(store.getFavoriteCount())
                .openingTime(store.getOpeningTime())
                .closingTime(store.getClosingTime())
                .lastOrderTime(store.getLastOrderTime())
                .storeState(storeState)
                .storeCongestionLevel(store.getStoreCongestionLevel())
                .userCongestionLevel(store.getUserCongestionLevel())
                .menuDetailResDtoList(menuDetailResDtoList)
                .recommendStoreResDtoList(recommendStoreResDtoList)
                .build();
    }

    public static RecommendStoreResDto recommendStoreResDto(Store store) {
        return RecommendStoreResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .storeCongestionLevel(store.getStoreCongestionLevel())
                .userCongestionLevel(store.getUserCongestionLevel())
                .address(store.getAddress())
                .build();
    }

    public static StoreDetailFromCafeDto storeDetailFromCafeDto(Store store) {

        return StoreDetailFromCafeDto.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .storeCongestionLevel(store.getStoreCongestionLevel())
                .userCongestionLevel(store.getUserCongestionLevel())
                .build();
    }

    public static StoreCongestionFromUserDto storeCongestionFromUserDto(Store store) {

        return StoreCongestionFromUserDto.builder()
                .storeCongestionLevel(store.getStoreCongestionLevel())
                .userCongestionLevel(store.getUserCongestionLevel())
                .build();
    }

    public static StorePosDto storePosDto(Store store) {

        return StorePosDto.builder()
                .id(store.getId())
                .latitude(store.getLatitude())
                .longitude((store.getLongitude()))
                .address(store.getAddress())
                .build();
    }

    public static StorePosListDto storePosListDto(List<Store> storeList) {

        List<StorePosDto> storePosDtoList = storeList.stream()
                .map(StoreConverter::storePosDto)
                .toList();

        return StorePosListDto.builder()
                .storePosDtoList(storePosDtoList)
                .build();
    }

}
