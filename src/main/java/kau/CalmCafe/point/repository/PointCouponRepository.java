package kau.CalmCafe.point.repository;

import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.PointCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointCouponRepository extends JpaRepository<PointCoupon, Long> {
}
