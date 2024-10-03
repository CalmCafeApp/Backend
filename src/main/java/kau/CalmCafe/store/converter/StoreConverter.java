package kau.CalmCafe.store.converter;

import kau.CalmCafe.store.domain.Store;
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
                .build();
    }
}
