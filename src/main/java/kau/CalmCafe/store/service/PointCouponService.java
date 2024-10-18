package kau.CalmCafe.store.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.store.converter.PointCouponConverter;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.PointCoupon;
import kau.CalmCafe.store.repository.PointCouponRepository;
import kau.CalmCafe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointCouponService {

    private final PointCouponRepository pointCouponRepository;

    @Transactional
    public PointCoupon createPointCoupon(User user, Menu menu) {

        PointCoupon pointCoupon = PointCouponConverter.savePointCoupon(user, menu);
        pointCouponRepository.save(pointCoupon);

        return pointCoupon;

    }
}
