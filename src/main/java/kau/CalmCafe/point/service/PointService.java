package kau.CalmCafe.point.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.point.converter.PointConverter;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.point.domain.PointCoupon;
import kau.CalmCafe.point.repository.PointCouponRepository;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

    private final PointCouponRepository pointCouponRepository;

    @Transactional
    public PointCoupon createPointCoupon(User user, Menu menu) {

        if (user.getPoint() < menu.getPointPrice()) {
            throw GeneralException.of(ErrorCode.POINT_BUY_FAILED);
        }

        PointCoupon pointCoupon = PointConverter.savePointCoupon(user, menu);
        pointCouponRepository.save(pointCoupon);

        return pointCoupon;

    }

    @Transactional
    public List<PointCoupon> getMyPointCouponList(User user) {
        return pointCouponRepository.findAllByUser(user, LocalDate.now());
    }
}
