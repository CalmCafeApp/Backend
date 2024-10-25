package kau.CalmCafe.store.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.Congestion.domain.CongestionLevel;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    // Pagable 객체 수
    private static final Integer TOP_COUNT = 100;

    private final StoreRepository storeRepository;

    @Transactional
    public Store findByAddress(String address) {
        return storeRepository.findByAddress(address)
                .orElseThrow(() -> GeneralException.of(ErrorCode.STORE_NOT_FOUND));
    }

    @Transactional
    public Store findById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.STORE_NOT_FOUND));
    }

    @Transactional
    public Integer calDistance(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        // 지구 반지름 (단위: km)
        final int EARTH_RADIUS = 6371;

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 최종 거리 (단위: m)
        return (int) Math.round(EARTH_RADIUS * c * 1000);
    }

    @Transactional
    public List<Store> getNearStoreList(String address) {
        return storeRepository.findByAddressContaining(address);
    }

    @Transactional
    public List<Store> getRecommendStoreList(Store store) {
        Double currentLatitude = store.getLatitude();
        Double currentLongitude = store.getLongitude();

        return storeRepository.findAll().stream()
                .filter(storeObject -> {
                    // 거리 계산
                    Integer distance = calDistance(
                            storeObject.getLatitude(),
                            storeObject.getLongitude(),
                            currentLatitude,
                            currentLongitude
                    );

                    // 1km 이내의 매장이 아닌 경우 필터링
                    if (distance > 1000) {
                        return false;
                    }

                    // 혼잡도 기준으로 매장을 필터링
                    CongestionLevel storeCongestion = storeObject.getStoreCongestionLevel();
                    CongestionLevel userCongestion = storeObject.getUserCongestionLevel();

                    return !(isBusy(storeCongestion) || isBusy(userCongestion));
                })
                .collect(Collectors.toList());
    }

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

    // 혼잡도 상태가 혼잡인지 확인
    private boolean isBusy(CongestionLevel level) {
        return level == CongestionLevel.BUSY;
    }

    public Store updateStoreHours(Store store, String openingTimeStr, String closingTimeStr) {
        if (store == null) {
            throw new IllegalArgumentException("매장 정보가 없습니다.");
        }

        LocalTime openingTime = LocalTime.parse(openingTimeStr);
        LocalTime closingTime = LocalTime.parse(closingTimeStr);

        store.updateOpeningTime(openingTime);
        store.updateClosingTime(closingTime);

        return storeRepository.save(store);
    }

    public Store updateLastOrderTime(Store store, String lastOrderTimeStr) {
        LocalTime lastOrderTime = LocalTime.parse(lastOrderTimeStr);
        store.updateLastOrderTime(lastOrderTime);
        return storeRepository.save(store);
    }

    public Store updateMaxCapacity(Store store, Integer maxCapacity) {
        store.updateMaxCustomerCount(maxCapacity);
        return storeRepository.save(store);
    }
}
