package com.E_Commerce.repositories;

import com.E_Commerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,  Long> {

    Optional<User> findByUserName(String name);
}
