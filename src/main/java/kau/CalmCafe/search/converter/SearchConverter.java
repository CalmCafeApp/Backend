package kau.CalmCafe.search.converter;

import java.util.List;
import kau.CalmCafe.search.dto.SearchResponseDto.HomeSearchResDto;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.SearchStoreResDto;
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
}
