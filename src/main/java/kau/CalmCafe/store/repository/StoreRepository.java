package kau.CalmCafe.store.repository;

import kau.CalmCafe.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByAddress(String Address);

    List<Store> findByAddressContaining(String userAddress);
}
