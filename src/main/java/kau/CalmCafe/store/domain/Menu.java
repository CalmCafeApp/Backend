package kau.CalmCafe.store.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // 사용자들이 구매한 쿠폰 목록
    @OneToMany(mappedBy = "menu")
    private List<PointCoupon> pointCouponList;

    // 메뉴 이름
    @Column(nullable = false)
    private String name;

    // 메뉴 가격
    @Column(nullable = false)
    private Integer price;

    // 메뉴 이미지
    private String image;

    // 현재 메뉴 포인트 스토어 판매 할인율 (판매 중이 아니면 0)
    private Integer pointDiscount;

    // 현재 메뉴 포인트 스토어 판매 가격
    private Integer pointPrice;

}
