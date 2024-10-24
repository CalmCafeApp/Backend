package kau.CalmCafe.congestion.repository;

import kau.CalmCafe.congestion.domain.CongestionInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongestionInputRepository extends JpaRepository<CongestionInput, Long>  {
}
