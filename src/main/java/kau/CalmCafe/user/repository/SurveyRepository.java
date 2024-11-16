package kau.CalmCafe.user.repository;

import kau.CalmCafe.user.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    boolean existsByUserId(Long userId);
}
