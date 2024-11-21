package kau.CalmCafe.congestion.service;

import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.global.s3.CsvParser;
import kau.CalmCafe.global.s3.S3Service;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CongestionPredictService {
    private final S3Service s3Service;
    private final CsvParser csvParser;
    private final StoreRepository storeRepository;

    @Scheduled(fixedRate = 600000) // 1분마다 실행
    @Transactional
    public void updateCongestionPredictions() {
        System.out.println("스케줄 실행!!");
        List<Store> stores = storeRepository.findAll();

        // 현재 날짜와 시간 정보
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();
        int currentHour = now.getHour();

        // 요일 문자열로 변환 (Sunday, Monday, ...)
        String currentWeekday = currentDayOfWeek.toString().toLowerCase();
        currentWeekday = currentWeekday.substring(0, 1).toUpperCase() + currentWeekday.substring(1);

        for (Store store : stores) {
            try {
                String filePath = "congestion/" + store.getId() + ".csv";

                // S3 파일 존재 여부 확인
                if (!s3Service.doesFileExist(filePath)) {
                    // log.info("File not found for store ID {}: {}", store.getId(), filePath);
                    continue; // 파일이 없으면 다음 매장으로 넘어감
                }

                InputStream csvInputStream = s3Service.downloadFile(filePath);
                // log.info("S3 파일 가져오기 성공! Store ID: {}", store.getId());

                // CSV 데이터 파싱
                Map<String, Map<Integer, Integer>> congestionData = csvParser.parse(csvInputStream);
                // log.info("CSV 파싱 성공! Store ID: {}", store.getId());

                // 현재 요일과 시간에 맞는 값으로 업데이트
                Map<Integer, Integer> hourlyData = congestionData.get(currentWeekday);
                if (hourlyData != null) {
                    Integer predictedPeople = hourlyData.get(currentHour);
                    if (predictedPeople != null) {
                        store.updatePredictedCongestion(predictedPeople);
                        log.info("Store ID: {} - Congestion Updated: {} people", store.getId(), predictedPeople);
                    } else {
                        log.warn("Store ID: {} - No data for current hour: {}", store.getId(), currentHour);
                    }
                } else {
                    log.warn("Store ID: {} - No data for current weekday: {}", store.getId(), currentWeekday);
                }

                storeRepository.save(store);
            } catch (Exception e) {
                // log.error("Store ID: {} - Error occurred while updating congestion predictions: {}", store.getId(), e.getMessage(), e);
                throw new GeneralException(ErrorCode.STORE_CONGESTION_UPDATE_FAIL);
            }
        }
    }

}
