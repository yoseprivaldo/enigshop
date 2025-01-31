package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;
import com.enigmacamp.enigshop.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtService jwtService;


    @PostMapping("/token")
    public ResponseEntity<CommonResponse<Object>> generateToken(@RequestBody UserAccount userAccount){

        String token = jwtService.generateToken(userAccount);

        Map<String, String> tokenData = Map.of("token", token);

        CommonResponse<Object> response = CommonResponse.<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("Succesfully generated token")
                .data(tokenData)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<CommonResponse<Boolean>> verifyToken(@RequestHeader("Authorization") String token){
        Boolean isValid = jwtService.verifyJwtToken(token);
        CommonResponse<Boolean> response = CommonResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message("Token is valid")
                .data(isValid)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/claims")
    public ResponseEntity<CommonResponse<JwtClaims>> getClaims(@RequestHeader("Authorization") String token){
        JwtClaims claims = jwtService.getClaimsByToken(token);
        CommonResponse<JwtClaims> response = CommonResponse.<JwtClaims>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved claims")
                .data(claims)
                .build();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleException(Exception e){
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Invalid or expired token")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }



}
