package kau.CalmCafe.promotion.converter;

import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.promotion.dto.PromotionResponseDto.PromotionDetailResDto;
import kau.CalmCafe.promotion.dto.PromotionUsedState;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionConverter {

    public static PromotionDetailResDto promotionDetailResDto(User user, Promotion promotion) {

        PromotionUsedState promotionUsedState = promotion.getPromotionUsedList().stream()
                .anyMatch(promotionUsed -> promotionUsed.getUser().equals(user))
                ? PromotionUsedState.USED
                : PromotionUsedState.NOT_USED;

        return PromotionDetailResDto.builder()
                .id(promotion.getId())
                .promotionType(promotion.getPromotionType())
                .startTime(promotion.getStartTime())
                .endTime(promotion.getEndTime())
                .discount(promotion.getDiscount())
                .promotionUsedState(promotionUsedState)
                .build();
    }

}
