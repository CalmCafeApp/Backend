package kau.CalmCafe.point.converter;

import java.util.List;
import kau.CalmCafe.point.dto.PointResponseDto;
import kau.CalmCafe.point.dto.PointResponseDto.PointCouponResDto;
import kau.CalmCafe.point.dto.PointResponseDto.PointCouponResListDto;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.point.domain.PointCoupon;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PointConverter {

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

    public static PointCouponResDto pointCouponResDto(PointCoupon pointCoupon) {
        return PointCouponResDto.builder()
                .id(pointCoupon.getId())
                .storeName(pointCoupon.getMenu().getStore().getName())
                .menuName(pointCoupon.getMenu().getName())
                .discount(pointCoupon.getDiscount())
                .expirationStart(pointCoupon.getExpirationStart())
                .expirationEnd(pointCoupon.getExpirationEnd())
                .build();
    }

    public static PointCouponResListDto pointCouponResListDto(List<PointCoupon> pointCouponList) {
        List<PointCouponResDto> pointResponseDtoList = pointCouponList.stream()
                .map(PointConverter::pointCouponResDto)
                .toList();

        return PointCouponResListDto.builder()
                .pointCouponResDtoList(pointResponseDtoList)
                .build();
    }

}
