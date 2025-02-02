package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.request.AuthRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(APIUrl.USER_ACCOUNT_API)
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserAccount>> createUser(
            @RequestBody AuthRequest request
            ){
        UserAccount createdUser = userAccountService.createCustomerAccount(request.getUsername(), request.getPassword());
        return mapToResponseEntity(
                HttpStatus.CREATED,
                "User Account Created Successfully",
                createdUser,
                null
        );
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserAccount>> getUser(@RequestBody AuthRequest request) {
        UserAccount user = userAccountService.getUserByPasswordAndUsername(request.getUsername(), request.getPassword());
        return mapToResponseEntity(
                HttpStatus.OK, "User found", user, null
        );
    }

}
