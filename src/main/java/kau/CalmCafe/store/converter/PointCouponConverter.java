package kau.CalmCafe.store.converter;

import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.PointCoupon;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PointCouponConverter {

    // 유효기간 (년)
    private static final Integer EXPIRATION_PERIOD_YEARS = 1;

    public static PointCoupon savePointCoupon(User user, Menu menu) {
        return PointCoupon.builder()
                .menu(menu)
                .user(user)
                .discount(menu.getPointDiscount())
                .expirationStart(LocalDate.now())
                .expirationEnd(LocalDate.now().plusYears(EXPIRATION_PERIOD_YEARS))
                .build();
    }

}
