package kau.CalmCafe.CafeCongestion.repository;

import kau.CalmCafe.CafeCongestion.domain.Congestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CongestionInputRepository extends JpaRepository<Congestion, Long>  {
}
