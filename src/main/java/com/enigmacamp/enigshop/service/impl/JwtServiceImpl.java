package com.enigmacamp.enigshop.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;
import com.enigmacamp.enigshop.service.JwtService;
import com.enigmacamp.enigshop.utils.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    @Value("${app.enigshop.jwt.jwt-secret}")
    private String jwtSecret;

    @Value("${app.enigshop.jwt.expired}")
    private long jwtExpiration;

    @Override
    public String generateToken(UserAccount userAccount) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration * 1000);

        List<String> roleWithPrefix = userAccount.getRoles().stream().map(role -> "ROLE_" + role.getName().name()).toList();

        return JWT.create()
                .withSubject(userAccount.getId())
                .withClaim("roles", roleWithPrefix)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    @Override
    public boolean verifyJwtToken(String token) {
        try{
            String parsedToken = parseJwt(token);
            if(parsedToken == null){
                throw new BadRequestException("Invalid or expired token");
            }

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
            verifier.verify(parsedToken);
            return true;
        } catch (JWTVerificationException e){
           throw new JWTVerificationException(e.getMessage());
        }
    }


    @Override
    public JwtClaims getClaimsByToken(String token) {
        try{

        String parsedToken = parseJwt(token);
        if(parsedToken == null){
            throw new BadRequestException("Invalid or expired token");
        }

        DecodedJWT decodedJWT = JWT.decode(parsedToken);
        return JwtClaims.builder()
                .userAccountId(decodedJWT.getSubject())
                .roles(decodedJWT.getClaim("roles").asList(String.class))
                .build();
        } catch (JWTVerificationException e){
            throw new JWTVerificationException(e.getMessage());
        }
    }

    @Override
    public String extractUsername(String token) {
        String parseToken = parseJwt(token);
        DecodedJWT decodedJWT = JWT.decode(parseToken);
        return decodedJWT.getSubject();
    }

    private String parseJwt(String token) {
        if(token!= null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
