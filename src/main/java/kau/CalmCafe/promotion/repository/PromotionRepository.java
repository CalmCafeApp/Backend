package kau.CalmCafe.promotion.repository;

import kau.CalmCafe.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
