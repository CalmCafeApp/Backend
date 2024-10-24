package kau.CalmCafe.promotion.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.store.domain.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "promotion")
    @Builder.Default
    private List<PromotionUsed> promotionUsedList = new ArrayList<>();

    // 할인율
    @Column(nullable = false)
    private Integer discount;

    // 프로모션 시작 시간
    @Column(nullable = false)
    private LocalTime startTime;

    // 프로모션 종료 시간
    @Column(nullable = false)
    private LocalTime endTime;

    // 프로모션 종류 (IN_STORE, TAKE_OUT)
    @Column(nullable = false)
    private PromotionType promotionType;

}
