package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsernameAndPassword(String username, String password);
}
