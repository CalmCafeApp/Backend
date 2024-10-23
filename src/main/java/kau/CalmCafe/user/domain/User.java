package kau.CalmCafe.user.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.store.domain.PointCoupon;
import kau.CalmCafe.store.domain.PromotionUsed;
import kau.CalmCafe.store.domain.Store;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;

    // 보유 포인트 쿠폰 목록
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<PointCoupon> pointCouponList = new ArrayList<>();

    // 참여 완료한 프로모션 목록
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<PromotionUsed> promotionUsedList = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Role role;

    private String provider;

    private String profileImage;

    private Integer point;

    public User(String username, String nickname, String email, String provider, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
        this.role = role;
    }
}
