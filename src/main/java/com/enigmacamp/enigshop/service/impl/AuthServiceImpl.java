package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.constant.UserRole;
import com.enigmacamp.enigshop.entity.Customer;
import com.enigmacamp.enigshop.entity.Role;
import com.enigmacamp.enigshop.entity.UserAccount;
import com.enigmacamp.enigshop.entity.dto.request.AuthRequest;
import com.enigmacamp.enigshop.entity.dto.request.NewCustomerRequest;
import com.enigmacamp.enigshop.entity.dto.request.NewSpecificUserRequest;
import com.enigmacamp.enigshop.entity.dto.response.LoginResponse;
import com.enigmacamp.enigshop.entity.dto.response.RegisterResponse;
import com.enigmacamp.enigshop.repository.UserAccountRepository;
import com.enigmacamp.enigshop.service.AuthService;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.service.JwtService;
import com.enigmacamp.enigshop.service.RoleService;
import com.enigmacamp.enigshop.utils.exception.BadCredentialException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(NewCustomerRequest request){
        // Todo: hashing password
        String hasPassword = passwordEncoder.encode(request.getPassword());

        // Todo: get or insert new role
        Role customerRole = roleService.getOrSave(UserRole.CUSTOMER);

        // Todo: validate username must be unique
        boolean isPresent = userAccountRepository.findByUsername(request.getUsername()).isPresent();
        if(isPresent){
            throw new BadCredentialException("Username already exists");
        }

        // Todo: insert new UserAccount
        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hasPassword)
                .roles(List.of(customerRole))
                .isActive(true)
                .build();


        userAccount = userAccountRepository.saveAndFlush(userAccount);

        // Todo: Insert new Customer
        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .userAccount(userAccount)
                .build();

        customerService.create(customer);

        // Todo: Return register response
        return RegisterResponse.builder()
                .userId(userAccount.getId())
                .roles(List.of(customerRole.getName().toString()))
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse registerSpecific(NewSpecificUserRequest request) {
        // Todo: hashing password
        String hasPassword = passwordEncoder.encode(request.getPassword());

        List<UserRole> roles = request.getRoles();
        List<Role> rolesToSave = new ArrayList<>();

        // Todo: get or insert new role
        for (var role : roles) {
            Role savedRole = roleService.getOrSave(role);
            rolesToSave.add(savedRole);
        }

        // Todo: validate username must be unique
        boolean isPresent = userAccountRepository.findByUsername(request.getUsername()).isPresent();
        if(isPresent){
            throw new BadCredentialException("Username already exists");
        }

        // Todo: insert new UserAccount
        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hasPassword)
                .roles(rolesToSave)
                .isActive(true)
                .build();


        userAccount = userAccountRepository.saveAndFlush(userAccount);

        // Todo: Insert new Role
        return RegisterResponse.builder()
                .userId(userAccount.getId())
                .roles(rolesToSave.stream().map(role -> role.getName().toString()).toList())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken(userAccount);

        return LoginResponse.builder()
                .userId(userAccount.getId())
                .username(userAccount.getUsername())
                .token(token)
                .role(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
