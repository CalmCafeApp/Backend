package kau.CalmCafe.promotion.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.promotion.dto.PromotionResponseDto;
import kau.CalmCafe.promotion.repository.PromotionRepository;
import kau.CalmCafe.promotion.domain.Promotion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

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

    @Transactional
    public PromotionResponseDto.PromotionDetailResDto updatePromotionDiscount(Long promotionId, Integer discount) {
        Promotion promotion = findById(promotionId);
        promotion.updateDiscount(discount);
        Promotion updatedPromotion = promotionRepository.save(promotion);

        return convertToDetailResDto(updatedPromotion);
    }

    @Transactional
    public PromotionResponseDto.PromotionDetailResDto updatePromotionPeriod(Long promotionId, String startTimeStr, String endTimeStr) {
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        Promotion promotion = findById(promotionId);
        promotion.updatePeriod(startTime, endTime);
        Promotion updatedPromotion = promotionRepository.save(promotion);

        return convertToDetailResDto(updatedPromotion);
    }

    private PromotionResponseDto.PromotionDetailResDto convertToDetailResDto(Promotion promotion) {
        return PromotionResponseDto.PromotionDetailResDto.builder()
                .id(promotion.getId())
                .startTime(promotion.getStartTime())
                .endTime(promotion.getEndTime())
                .discount(promotion.getDiscount())
                .promotionType(promotion.getPromotionType())
                .build();
    }
}
