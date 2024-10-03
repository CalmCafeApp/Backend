package kau.CalmCafe.store.repository;

import jakarta.persistence.LockModeType;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.domain.StoreFavorite;
import kau.CalmCafe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface StoreFavoriteRepository extends JpaRepository<StoreFavorite, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<StoreFavorite> findByUserAndStore(User user, Store store);
}
