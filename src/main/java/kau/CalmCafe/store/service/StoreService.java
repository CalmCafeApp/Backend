package kau.CalmCafe.store.service;

import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import kau.CalmCafe.congestion.domain.CongestionLevel;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.dto.StoreResponseDto;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final StoreRepository storeRepository;
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

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

    // 혼잡도 상태가 혼잡 또는 매우 혼잡인지 확인
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

    public void scheduleStoreTasks() {
        List<Store> storeList = storeRepository.findAll();

        storeList.forEach(store -> {
            validateClosingTime(store);
            LocalDateTime closingTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), store.getClosingTime());
            Duration delay = Duration.between(LocalDateTime.now(), closingTime);

            // 현재 시간 이후로 스케줄링되지 않은 경우 다음날로 설정
            if (delay.isNegative() || delay.isZero()) {
                closingTime = closingTime.plusDays(1);
            }

            taskScheduler.schedule(() -> updateUserCongestionInputTime(store),
                    closingTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
        });
    }

    public void updateStoreSchedule(Store store) {
        // 기존 작업이 있다면 취소
        ScheduledFuture<?> existingTask = scheduledTasks.get(store.getId());
        if (existingTask != null && !existingTask.isCancelled()) {
            existingTask.cancel(false); // 취소
        }

        // 새로운 Closing Time에 작업 등록
        LocalDateTime closingTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), store.getClosingTime());
        if (Duration.between(LocalDateTime.now(), closingTime).isNegative()) {
            closingTime = closingTime.plusDays(1); // Closing Time이 지났다면 다음날로 이동
        }

        ScheduledFuture<?> newTask = taskScheduler.schedule(() -> updateUserCongestionInputTime(store),
                closingTime.atZone(ZoneId.systemDefault()).toInstant());

        // 새 작업을 저장
        scheduledTasks.put(store.getId(), newTask);
    }

    private void updateUserCongestionInputTime(Store store) {
        store.updateUserCongestionInputTime(
                getClosestOpeningTime(store.getOpeningTime()));
        storeRepository.save(store);
    }

    private void validateClosingTime(Store store) {
        if (store.getClosingTime() == null) {
            throw new IllegalArgumentException("Store closing time must not be null: " + store.getId());
        }
    }

    private LocalDateTime getClosestOpeningTime(LocalTime openingTime) {
        LocalDateTime todayOpening = LocalDateTime.of(LocalDateTime.now().toLocalDate(), openingTime);

        // 현재 시간이 오늘의 OpeningTime 이전이면 오늘을 반환
        if (LocalDateTime.now().isBefore(todayOpening)) {
            return todayOpening;
        }
        // 현재 시간이 오늘의 OpeningTime 이후면 내일의 OpeningTime 반환
        else {
            return todayOpening.plusDays(1);
        }
    }

    @Transactional
    public StoreResponseDto.StoreMenuResponseDto getStoreMenus(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(ErrorCode.STORE_NOT_FOUND));

        List<StoreResponseDto.StoreMenuResponseDto.MenuInfo> menuInfos = store.getMenuList().stream()
                .map(this::convertToMenuInfo)
                .collect(Collectors.toList());

        return StoreResponseDto.StoreMenuResponseDto.builder()
                .storeId(store.getId())
                .menus(menuInfos)
                .build();
    }

    private StoreResponseDto.StoreMenuResponseDto.MenuInfo convertToMenuInfo(Menu menu) {
        return StoreResponseDto.StoreMenuResponseDto.MenuInfo.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .image(menu.getImage())
                .build();
    }
}
