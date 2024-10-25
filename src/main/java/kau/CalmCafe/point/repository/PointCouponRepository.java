package kau.CalmCafe.point.repository;

import java.time.LocalDate;
import java.util.List;
import kau.CalmCafe.point.domain.PointCoupon;
import kau.CalmCafe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointCouponRepository extends JpaRepository<PointCoupon, Long> {

    // 유효 기간 내 쿠폰 리스트 반환
    @Query("SELECT p FROM PointCoupon p WHERE p.user = :user AND p.expirationEnd > :today")
    List<PointCoupon> findAllByUser(@Param("user") User user, @Param("today") LocalDate today);
}
