package com.enigmacamp.enigshop.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;
import com.enigmacamp.enigshop.repository.UserAccountRepository;
import com.enigmacamp.enigshop.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Value("${app.enigshop.jwt.jwt-secret}")
    private String jwtSecret;

    @Value("${app.enigshop.jwt.expired}")
    private long jwtExpiration;

    @Override
    public String generateToken(UserAccount userAccount) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration * 1000);

        return JWT.create()
                .withSubject(userAccount.getUsername())
                .withClaim("password", userAccount.getPassword())
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    @Override
    public boolean verifyJwtToken(String token) {
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e){
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }


    @Override
    public JwtClaims getClaimsByToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return JwtClaims.builder()
                .userAccountId(decodedJWT.getSubject())
                .roles(decodedJWT.getClaim("role").asList(String.class))
                .build();
    }

    private String parseJwt(String token) {
        if(token!= null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
