package kau.CalmCafe.promotion.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import kau.CalmCafe.promotion.domain.Promotion;
import kau.CalmCafe.promotion.domain.PromotionUsed;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.domain.StoreFavorite;
import kau.CalmCafe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionUsedRepository extends JpaRepository<PromotionUsed, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PromotionUsed> findByUserAndPromotion(User user, Promotion promotion);

}
