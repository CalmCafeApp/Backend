package kau.CalmCafe.store.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
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
    public Integer calDistance(Double userLatitude, Double userLongitude, Double storeLatitude, Double storeLongitude) {
        // 지구 반지름 (단위: km)
        final int EARTH_RADIUS = 6371;

        double latDistance = Math.toRadians(storeLatitude - userLatitude);
        double lonDistance = Math.toRadians(storeLongitude - userLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(userLatitude)) * Math.cos(Math.toRadians(storeLatitude))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 최종 거리 (단위: m)
        return (int) Math.round(EARTH_RADIUS * c * 1000);

    }
}
