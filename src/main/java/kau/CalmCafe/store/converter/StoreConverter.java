package kau.CalmCafe.store.converter;

import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreCongestionFromUserDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailFromCafeDto;
import kau.CalmCafe.store.dto.StoreResponseDto.StoreDetailResDto;
import kau.CalmCafe.store.dto.StoreState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class StoreConverter {

    public static StoreDetailResDto storeDetailResDto(Store store, Integer distance) {

        // 현재 시간
        LocalTime now = LocalTime.now();

        // 현재 매장 상태
        StoreState storeState = (now.isAfter(store.getOpeningTime()) && now.isBefore(store.getClosingTime())) ? StoreState.OPEN : StoreState.CLOSE;

        return StoreDetailResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .distance(distance)
                .favoriteCount(store.getFavoriteCount())
                .openingTime(store.getOpeningTime())
                .closingTime(store.getClosingTime())
                .lastOrderTime(store.getLastOrderTime())
                .storeState(storeState)
                .storeCongestionValue(store.getStoreCongestionValue())
                .userCongestionValue(store.getUserCongestionValue())
                .build();
    }

    public static StoreDetailFromCafeDto storeDetailFromCafeDto(Store store) {
        return StoreDetailFromCafeDto.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .storeCongestionValue(store.getStoreCongestionValue())
                .userCongestionValue(store.getUserCongestionValue())
                .build();
    }

    public static StoreCongestionFromUserDto storeCongestionFromUserDto(Store store) {
        return StoreCongestionFromUserDto.builder()
                .storeCongestionValue(store.getStoreCongestionValue())
                .userCongestionValue(store.getUserCongestionValue())
                .build();
    }
}
