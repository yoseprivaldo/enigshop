package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;

public interface JwtService {
    String generateToken(String username, String password);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
