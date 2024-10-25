package kau.CalmCafe.promotion.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.promotion.converter.PromotionUsedConverter;
import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.promotion.domain.PromotionUsed;
import kau.CalmCafe.promotion.repository.PromotionUsedRepository;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionUsedService {

    private final PromotionUsedRepository promotionUsedRepository;

    @Transactional
    public Long createPromotionUsed(User user, Promotion promotion) {
        PromotionUsed promotionUsedForCheck = promotionUsedRepository.findByUserAndPromotion(user, promotion).orElse(null);

        if (promotionUsedForCheck != null) {
            throw new GeneralException(ErrorCode.PROMOTION_ALREADY_FAVORITE);
        }
        else {
            PromotionUsed newPromotionUsed = PromotionUsedConverter.PromotionUsed(user, promotion);
            promotionUsedRepository.save(newPromotionUsed);
            return newPromotionUsed.getId();
        }
    }

}
