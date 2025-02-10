package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.request.AuthRequest;
import com.enigmacamp.enigshop.entity.dto.request.NewCustomerRequest;
import com.enigmacamp.enigshop.entity.dto.request.NewSpecificUserRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.JwtClaims;
import com.enigmacamp.enigshop.entity.dto.response.LoginResponse;
import com.enigmacamp.enigshop.entity.dto.response.RegisterResponse;
import com.enigmacamp.enigshop.service.AuthService;
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
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<CommonResponse<Object>> generateToken(@RequestBody UserAccount request){
        String token = jwtService.generateToken(request);
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


    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody AuthRequest authRequest){
        LoginResponse loginResponse = authService.login(authRequest);

        return mapToResponseEntity(
                HttpStatus.OK,
                "Login Success",
                loginResponse,
                null
        );
    }


    @PostMapping(path = "/register/customer")
    public ResponseEntity<CommonResponse<RegisterResponse>> register(@RequestBody NewCustomerRequest request){

        RegisterResponse registerResponse = authService.register(request);

        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Register success",
                registerResponse,
                null
        );
    }

    @PostMapping(path = "/register/admin")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerSpecific(@RequestBody NewSpecificUserRequest request){

        RegisterResponse registerResponse = authService.registerSpecific(request);

        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Register success",
                registerResponse,
                null
        );
    }

}
