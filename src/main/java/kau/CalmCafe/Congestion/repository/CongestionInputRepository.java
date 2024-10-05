package kau.CalmCafe.Congestion.repository;

import kau.CalmCafe.Congestion.domain.CongestionInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongestionInputRepository extends JpaRepository<CongestionInput, Long>  {
}
