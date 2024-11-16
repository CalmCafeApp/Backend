package kau.CalmCafe.store.domain;

import jakarta.persistence.*;
import kau.CalmCafe.congestion.domain.CongestionInput;
import kau.CalmCafe.congestion.domain.CongestionLevel;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<StoreFavorite> storeFavoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<CongestionInput> congestionInputList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<Promotion> promotionList = new ArrayList<>();

    // 위도
    private Double latitude;

    // 이미지
    private String image;

    // 경도
    private Double longitude;

    // 최대 수용 인원 수
    private Integer maxCustomerCount;

    // 현재 매장 인원 수
    private Integer currentCustomerCount;

    // 운영 시작 시간
    private LocalTime openingTime;

    // 운영 종료 시간
    private LocalTime closingTime;

    // 라스트 오더 시간
    private LocalTime lastOrderTime;

    // 즐겨찾기 수
    private Integer favoriteCount;

    // 매장 측 혼잡도 수치
    private Integer storeCongestionValue;

    // 매장 측 혼잡도 등급
    private CongestionLevel storeCongestionLevel;

    // 사용자 측 혼잡도 수치
    private Integer userCongestionValue;

    // 사용자 측 혼잡도 등급
    private CongestionLevel userCongestionLevel;

    // 사용자 측 혼잡도 기록 시간
    private LocalDateTime userCongestionInputTime;

    public Integer increaseFavoriteCount() {
        this.favoriteCount += 1;
        return this.favoriteCount;
    }

    public Integer decreaseFavoriteCount() {
        this.favoriteCount -= 1;
        return this.favoriteCount;
    }

    public CongestionLevel updateUserCongestion(Integer congestionValue) {
        this.userCongestionValue = congestionValue;
        this.userCongestionInputTime = LocalDateTime.now();
        this.userCongestionLevel = calculateCongestionLevel(congestionValue);

        return this.userCongestionLevel;
    }

    public CongestionLevel updateStoreCongestion(Integer congestionValue) {
        this.storeCongestionValue = congestionValue;
        this.storeCongestionLevel = calculateCongestionLevel(congestionValue);

        return this.storeCongestionLevel;
    }

    private CongestionLevel calculateCongestionLevel(Integer congestionValue) {
        if (congestionValue <= 33) {
            return CongestionLevel.CALM;
        } else if (congestionValue <= 66) {
            return CongestionLevel.NORMAL;
        } else{
            return CongestionLevel.BUSY;
        }
    }
    public void updateOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public void updateClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public void updateLastOrderTime(LocalTime lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }

    public void updateMaxCustomerCount(Integer maxCustomerCount) {
        this.maxCustomerCount = maxCustomerCount;
    }

    public void updateUserCongestionInputTime(LocalDateTime updateTime) {
        this.userCongestionInputTime = updateTime;
    }
}
