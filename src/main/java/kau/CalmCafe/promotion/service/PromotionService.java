package kau.CalmCafe.promotion.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.promotion.converter.PromotionConverter;
import kau.CalmCafe.promotion.domain.PromotionType;
import kau.CalmCafe.promotion.dto.PromotionResponseDto;
import kau.CalmCafe.promotion.repository.PromotionRepository;
import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static kau.CalmCafe.promotion.converter.PromotionConverter.convertToDetailResDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final StoreRepository storeRepository;

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

    @Transactional
    public List<PromotionResponseDto.PromotionDetailResDto> getPromotionsByStoreId(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(ErrorCode.STORE_NOT_FOUND));

        return store.getPromotionList().stream()
                .map(PromotionConverter::convertToDetailResDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PromotionResponseDto.PromotionDetailResDto createPromotion(Long storeId, Integer discount, LocalTime startTime, LocalTime endTime, PromotionType promotionType) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(ErrorCode.STORE_NOT_FOUND));

        Promotion promotion = Promotion.builder()
                .store(store)
                .discount(discount)
                .startTime(startTime)
                .endTime(endTime)
                .promotionType(promotionType)
                .build();

        Promotion savedPromotion = promotionRepository.save(promotion);
        return convertToDetailResDto(savedPromotion);
    }

    @Transactional
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new GeneralException(ErrorCode.PROMOTION_NOT_FOUND));
        promotionRepository.delete(promotion);
    }
}
