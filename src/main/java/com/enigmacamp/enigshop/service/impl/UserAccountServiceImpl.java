package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImpl {

    @Autowired
    UserAccountRepository userAccountRepository;

    public UserAccount getUserByPasswordAndUsername(String username, String password){
        return userAccountRepository.findByUserNameAndPassword(username, password).get();
    }


}
