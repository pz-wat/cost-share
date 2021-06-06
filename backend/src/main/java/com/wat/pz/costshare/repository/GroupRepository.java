package com.wat.pz.costshare.repository;

import com.wat.pz.costshare.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByAccessCode(String accessCode);

    @Modifying
    @Query("DELETE from UserGroup ug where ug.user.id=:userId and ug.group.id=:groupId")
    void removeUserFromGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);
}
