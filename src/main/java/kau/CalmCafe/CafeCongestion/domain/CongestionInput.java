package kau.CalmCafe.CafeCongestion.domain;

import jakarta.persistence.*;
import kau.CalmCafe.global.entity.BaseEntity;
import kau.CalmCafe.user.domain.User;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "congestion_input")
public class CongestionInput extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congestion_id")
    private Congestion congestion;


    private Integer congestionInputValue;
}