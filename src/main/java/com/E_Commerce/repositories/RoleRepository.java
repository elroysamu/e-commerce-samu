package com.E_Commerce.repositories;

import com.E_Commerce.models.AppRole;
import com.E_Commerce.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
