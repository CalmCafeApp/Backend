package kau.CalmCafe.search.converter;

import java.time.LocalDateTime;
import java.util.List;
import kau.CalmCafe.search.domain.SearchLog;
import kau.CalmCafe.search.dto.SearchResponseDto.HomeSearchResDto;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.SearchStoreResDto;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchConverter {
    public static HomeSearchResDto homeSearchResDto(Store storeNearest, List<SearchStoreResDto> searchStoreResDtoList) {
        return HomeSearchResDto.builder()
                .latitude(storeNearest.getLatitude())
                .longitude(storeNearest.getLongitude())
                .searchStoreResDtoList(searchStoreResDtoList)
                .build();
    }

    public static SearchLog saveSearchLog(User user, String query, LocalDateTime now) {
        return SearchLog.builder()
                .user(user)
                .keyword(query)
                .searchTime(now)
                .build();
    }
}
