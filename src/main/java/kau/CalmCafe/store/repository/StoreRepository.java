package kau.CalmCafe.store.repository;

import kau.CalmCafe.store.domain.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByAddress(String Address);

    List<Store> findByAddressContaining(String address);

    @Query("SELECT s FROM Store s WHERE s.address LIKE %:location% ORDER BY " +
            "s.userCongestionValue + s.storeCongestionValue DESC, " +
            "s.userCongestionValue DESC, " +
            "s.storeCongestionValue DESC ")
    List<Store> findRankingStoreListByCongestion(@Param("location")String location, Pageable pageable);

    @Query("SELECT s FROM Store s WHERE s.address LIKE %:location% ORDER BY " +
            "SIZE(s.congestionInputList) DESC ")
    List<Store> findRankingStoreListByTotalVisit(@Param("location")String location, Pageable pageable);

    @Query("SELECT s FROM Store s WHERE s.address LIKE %:location% ORDER BY " +
            "s.favoriteCount DESC ")
    List<Store> findRankingStoreListByFavorite(@Param("location")String location, Pageable pageable);


    @Query("SELECT s FROM Store s WHERE s.name LIKE %:query% OR s.address LIKE %:query% " +
            "ORDER BY (6371 * acos(cos(radians(:userLatitude)) * cos(radians(s.latitude)) * " +
            "cos(radians(s.longitude) - radians(:userLongitude)) + sin(radians(:userLatitude)) * sin(radians(s.latitude)))) ASC")
    List<Store> findHomeSearchStoreListByQueryOrderByDistance(@Param("query") String query,
                                                              @Param("userLatitude") Double userLatitude,
                                                              @Param("userLongitude") Double userLongitude,
                                                              Pageable pageable);
}
