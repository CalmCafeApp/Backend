    package kau.CalmCafe.congestion.service;

    import java.time.Duration;
    import java.time.LocalDateTime;
    import kau.CalmCafe.congestion.converter.CongestionInputConverter;
    import kau.CalmCafe.congestion.domain.CongestionInput;
    import kau.CalmCafe.congestion.domain.CongestionLevel;
    import kau.CalmCafe.congestion.repository.CongestionInputRepository;
    import kau.CalmCafe.store.domain.Store;
    import kau.CalmCafe.store.repository.StoreRepository;
    import kau.CalmCafe.user.domain.User;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class CongestionInputService {
        private final CongestionInputRepository congestionInputRepository;
        private final StoreRepository storeRepository;

        public CongestionLevel inputAndRetrieveCongestion(User user, Integer congestionValue, Store store) {
            // 입력값 유효성 검사
            validateCongestionValue(congestionValue);

            CongestionInput congestionInput = CongestionInputConverter.saveCongestionInput(user, congestionValue, store);

            congestionInputRepository.save(congestionInput);

            // 포인트 지급
            LocalDateTime lastUpdated = store.getUserCongestionInputTime();
            int point = calculatePointsByMinutes(lastUpdated);
            user.addPoint(point);

            CongestionLevel congestionLevel = store.updateUserCongestion(congestionValue);
            storeRepository.save(store);
            return congestionLevel;
        }

        private void validateCongestionValue(Integer congestionValue) {
            if (congestionValue < 0 || congestionValue > 100) {
                throw new IllegalArgumentException("Congestion input value must be between 0 and 100");
            }
        }

        private int calculatePointsByMinutes(LocalDateTime lastUpdated) {
            if (lastUpdated == null) {
                return 0; // 이전 갱신 기록이 없으면 포인트 지급 없음
            }

            Duration duration = Duration.between(lastUpdated, LocalDateTime.now());
            long minutesAgo = duration.toMinutes();

            // 1분당 1포인트를 지급
            return (int) minutesAgo;
        }

    }