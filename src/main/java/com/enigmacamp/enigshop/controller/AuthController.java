package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.dto.request.AuthRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;
import com.enigmacamp.enigshop.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<CommonResponse<Object>> generateToken(@RequestBody AuthRequest request){
        String token = jwtService.generateToken(request.getUsername(), request.getPassword());
        Map<String, String> tokenData = Map.of("token", token);

        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Successfullly generated token",
                tokenData,
                null
        );
    }

    @GetMapping("/verify")
    public ResponseEntity<CommonResponse<Boolean>> verifyToken(@RequestHeader(value = "Authorization", required = true) String token){
        Boolean isValid = jwtService.verifyJwtToken(token);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Token is valid",
                isValid,
                null
        );
    }

    @GetMapping("/claims")
    public ResponseEntity<CommonResponse<JwtClaims>> getClaims(@RequestHeader(value = "Authorization", required = true) String token){
        JwtClaims claims = jwtService.getClaimsByToken(token);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Succesfull retrieved claims",
                claims,
                null
        );
    }


}
