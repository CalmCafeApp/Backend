package kau.CalmCafe.ranking.service;

import jakarta.transaction.Transactional;
import java.util.List;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingService {

    // Pagable 객체 수
    private static final Integer TOP_COUNT = 100;

    private final StoreRepository storeRepository;

    @Transactional
    public List<Store> getRankingStoreListByCongestion(String location) {

        Pageable pageable = PageRequest.of(0, TOP_COUNT);
        return storeRepository.findRankingStoreListByCongestion(location, pageable);

    }

    @Transactional
    public List<Store> getRankingStoreListByTotalVisit(String location) {

        Pageable pageable = PageRequest.of(0, TOP_COUNT);
        return storeRepository.findRankingStoreListByTotalVisit(location, pageable);

    }

    @Transactional
    public List<Store> getRankingStoreListByFavorite(String location) {

        Pageable pageable = PageRequest.of(0, TOP_COUNT);
        return storeRepository.findRankingStoreListByFavorite(location, pageable);

    }

}
