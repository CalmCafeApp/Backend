package kau.CalmCafe.search.service;

import java.util.Comparator;
import java.util.List;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.search.converter.SearchConverter;
import kau.CalmCafe.search.dto.SearchResponseDto.HomeSearchResDto;
import kau.CalmCafe.store.converter.StoreConverter;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto.SearchStoreResDto;
import kau.CalmCafe.store.repository.StoreRepository;
import kau.CalmCafe.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private static final Integer SEARCH_STORE_COUNT_LIMIT = 10;

    private final StoreRepository storeRepository;
    private final StoreService storeService;

    public HomeSearchResDto getHomeSearchResDto(final String query, Double userLatitude, Double userLongitude) {
        Pageable pageable = PageRequest.of(0, SEARCH_STORE_COUNT_LIMIT);

        // 주소와 이름에 query가 포함된 매장 탐색 (거리 순 정렬)
        List<Store> storeList = storeRepository.findHomeSearchStoreListByQueryOrderByDistance(query, userLatitude, userLongitude, pageable);

        if (storeList.isEmpty()) {
            throw new GeneralException(ErrorCode.HOME_RESEARCH_NOT_FOUND);
        }

        List<SearchStoreResDto> searchStoreResDtoList = storeList.stream()
                .map(StoreConverter::searchStoreResDto)
                .toList();

        Store storeNearest = storeList.get(0);

        return SearchConverter.homeSearchResDto(storeNearest, searchStoreResDtoList);
    }
}
