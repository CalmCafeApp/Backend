package kau.CalmCafe.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kau.CalmCafe.global.entity.BaseEntity;
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
@Table(name = "survey")
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String job;

    private String location;

    private String marriage;

    private String hobby;

    private String favoriteMenu;

    private String cafeUsingPurpose;

    private String cafeChooseCause;

    private String cafeVisitedFrequency;

    private String isUsingSNS;

    private String convenienceFacilityPrefer;
}
