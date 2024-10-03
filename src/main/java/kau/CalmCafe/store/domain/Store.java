package kau.CalmCafe.store.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import lombok.*;

import java.time.LocalTime;

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

    // 위도
    private Double latitude;

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
}
