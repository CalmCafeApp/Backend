package kau.CalmCafe.CafeCongestion.service;

import kau.CalmCafe.CafeCongestion.domain.Congestion;
import kau.CalmCafe.CafeCongestion.domain.CongestionInput;
import kau.CalmCafe.CafeCongestion.domain.CongestionLevel;
import kau.CalmCafe.CafeCongestion.repository.CongestionInputRepository;
import kau.CalmCafe.CafeCongestion.repository.CongestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CongestionInputService {

    private final CongestionInputRepository congestionInputRepository;
    private final CongestionRepository congestionRepository;

    public CongestionInputService(CongestionInputRepository congestionInputRepository, CongestionRepository congestionRepository) {
        this.congestionInputRepository = congestionInputRepository;
        this.congestionRepository = congestionRepository;
    }

    private void validateInputValue(Integer value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Congestion input value must be between 0 and 100");
        }
    }

    @Transactional
    public void processCongestionInput(CongestionInput input) {
        // 입력값 유효성 검사
        validateInputValue(input.getCongestionInputValue());

        Congestion congestion = input.getCongestion();
        // 혼잡도 값 설정
        congestion.setCongestionValue(input.getCongestionInputValue());
        // 혼잡도 레벨 계산 및 설정
        congestion.setCongestionLevel(calculateCongestionLevel(input.getCongestionInputValue()));
    }

    private CongestionLevel calculateCongestionLevel(Integer value) {
        if (value <= 25) {
            return CongestionLevel.EMPTY;
        } else if (value <= 50) {
            return CongestionLevel.NORMAL;
        } else if (value <= 75) {
            return CongestionLevel.CROWD;
        } else {
            return CongestionLevel.VERYCROWD;
        }
    }
}