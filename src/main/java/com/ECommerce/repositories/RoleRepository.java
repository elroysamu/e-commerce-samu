package com.ECommerce.repositories;

import com.ECommerce.models.AppRole;
import com.ECommerce.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
