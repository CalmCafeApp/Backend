package kau.CalmCafe.store.repository;

import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
}
