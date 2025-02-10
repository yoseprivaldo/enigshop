package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.dto.request.AuthRequest;
import com.enigmacamp.enigshop.entity.dto.request.NewCustomerRequest;
import com.enigmacamp.enigshop.entity.dto.request.NewSpecificUserRequest;
import com.enigmacamp.enigshop.entity.dto.response.LoginResponse;
import com.enigmacamp.enigshop.entity.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(NewCustomerRequest request);
    RegisterResponse registerSpecific(NewSpecificUserRequest request);
    LoginResponse login(AuthRequest request);
}
