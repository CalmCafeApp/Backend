package kau.CalmCafe.promotion.converter;

import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.promotion.domain.PromotionUsed;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionUsedConverter {

    public static PromotionUsed PromotionUsed(User user, Promotion promotion) {
        return PromotionUsed.builder()
                .user(user)
                .promotion(promotion)
                .build();
    }

}
