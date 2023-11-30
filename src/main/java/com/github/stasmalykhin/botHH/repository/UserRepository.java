package com.github.stasmalykhin.botHH.repository;

import com.github.stasmalykhin.botHH.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Stanislav Malykhin
 */
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByTelegramUserId(Long id);
}
