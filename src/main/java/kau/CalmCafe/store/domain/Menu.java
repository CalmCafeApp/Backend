package kau.CalmCafe.store.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.point.domain.PointCoupon;
import lombok.*;

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


    public void setImage(String image) {
        this.image = image != null ? image : this.image;
    }

    public void setPrice(Integer price) {
        this.price = price != null ? price : this.price;
    }

    public void setPointDiscount(Integer pointDiscount) {
        this.pointDiscount = pointDiscount != null ? pointDiscount : this.pointDiscount;
    }

    public void setPointPrice(Integer pointPrice) {
        this.pointPrice = pointPrice != null ? pointPrice : this.pointPrice;
    }
}
