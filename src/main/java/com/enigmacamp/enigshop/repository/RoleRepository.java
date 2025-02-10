package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.constant.UserRole;
import com.enigmacamp.enigshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(UserRole role);
}
