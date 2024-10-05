package kau.CalmCafe.CafeCongestion.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.user.domain.Role;
import lombok.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "congestion")
public class Congestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private CongestionLevel congestionLevel;

    private LocalTime recentTime;
    
    @Enumerated(EnumType.STRING)
    private Role inputRole;

    private Integer congestionValue;
}