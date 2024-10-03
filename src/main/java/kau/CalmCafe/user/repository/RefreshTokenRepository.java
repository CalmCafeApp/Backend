package kau.CalmCafe.user.repository;

import kau.CalmCafe.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    boolean existsById(String username);
    void deleteById(String username);
}
