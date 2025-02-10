package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.repository.UserAccountRepository;
import com.enigmacamp.enigshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount loadUserById(String id) throws UsernameNotFoundException {
        return userAccountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: "));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: "));
    }
}


