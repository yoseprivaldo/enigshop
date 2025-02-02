package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.UserAccount;

public interface UserAccountService {
    public UserAccount createCustomerAccount(String username, String password);
    public UserAccount getUserByPasswordAndUsername(String username, String password);
}
