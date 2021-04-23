package com.wat.pz.costshare.repository;

import com.wat.pz.costshare.entity.Role;
import com.wat.pz.costshare.type.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
