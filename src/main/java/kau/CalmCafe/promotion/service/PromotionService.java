package kau.CalmCafe.promotion.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.promotion.repository.PromotionRepository;
import kau.CalmCafe.promotion.domain.Promotion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Transactional
    public Promotion findById(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.PROMOTION_NOT_FOUND));
    }
}
