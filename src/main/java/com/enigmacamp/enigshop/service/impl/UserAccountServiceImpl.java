package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.constant.ROLE;
import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.repository.UserAccountRepository;
import com.enigmacamp.enigshop.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public UserAccount getUserByPasswordAndUsername(String username, String password){
        return userAccountRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserAccount createCustomerAccount(String username, String password) {
        UserAccount newUser = UserAccount.builder().username(username).password(password).role(ROLE.ROLES_CUSTOMER).build();
        return userAccountRepository.save(newUser);
    }
}
