package dev.awn.jwttokenservice.core.user.repository;

import java.util.Optional;

import dev.awn.jwttokenservice.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
