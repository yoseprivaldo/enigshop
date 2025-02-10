package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount loadUserById(String id);
}
