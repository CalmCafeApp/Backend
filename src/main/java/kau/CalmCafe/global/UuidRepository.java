package kau.CalmCafe.global;

import kau.CalmCafe.global.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
