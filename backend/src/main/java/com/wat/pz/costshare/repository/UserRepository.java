package com.wat.pz.costshare.repository;

import com.wat.pz.costshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
