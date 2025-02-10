package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;

import java.util.Date;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
    String extractUsername(String token);

}
