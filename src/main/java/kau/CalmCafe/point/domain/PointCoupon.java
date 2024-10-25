package kau.CalmCafe.point.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.user.domain.User;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "point_coupon")
public class PointCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 쿠폰 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    // 쿠폰 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 할인율
    @Column(nullable = false)
    private Integer discount;

    // 구매 일자
    @Column(nullable = false)
    private LocalDate expirationStart;

    // 유효 기간 (구매 후, 1년)
    @Column(nullable = false)
    private LocalDate expirationEnd;

}
