package com.ptithcm.onlinetest.repository;

import com.ptithcm.onlinetest.model.Role;
import com.ptithcm.onlinetest.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
