package kau.CalmCafe.user.repository;

import jakarta.transaction.Transactional;
import kau.CalmCafe.user.domain.Marriage;
import kau.CalmCafe.user.domain.Sex;
import kau.CalmCafe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 계정 이름으로 사용자 정보 탐색
    Optional<User> findByUsername(String username);

    // 사용자 이메일으로 사용자 정보 탐색
    Optional<User> findByEmail(String email);

    // 사용자 계정 이름을 가진 사용자 정보가 존재하는지 여부 탐색
    boolean existsByUsername(String username);

    // 사용자 이메일을 가진 사용자 정보가 존재하는지 여부 탐색
    boolean existsByEmail(String email);


    // 닉네임이 사용중인지 여부 탐색
    boolean existsByNickname(String nickname);

}