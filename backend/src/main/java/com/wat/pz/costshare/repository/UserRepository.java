package com.wat.pz.costshare.repository;

import com.wat.pz.costshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
