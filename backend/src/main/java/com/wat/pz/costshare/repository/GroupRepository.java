package com.wat.pz.costshare.repository;

import com.wat.pz.costshare.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByAccessCode(String accessCode);
}
