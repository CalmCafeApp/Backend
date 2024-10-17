package kau.CalmCafe.store.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import lombok.*;

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

    // 메뉴 이름
    @Column(nullable = false)
    private String name;

    // 메뉴 가격
    @Column(nullable = false)
    private Integer price;

    // 메뉴 이미지
    private String image;

    // 포인트 스토어 등록 여부
    private Boolean isPointStore;
}
