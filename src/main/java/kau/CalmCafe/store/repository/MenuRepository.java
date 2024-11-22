package kau.CalmCafe.store.repository;

import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m FROM Menu m WHERE m.store.id = :storeId AND m.pointDiscount > 0")
    List<Menu> findDiscountedMenusByStoreId(Long storeId);

    List<Menu> findAllByStore(Store store);
}
