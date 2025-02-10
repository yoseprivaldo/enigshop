package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.constant.UserRole;
import com.enigmacamp.enigshop.entity.Role;
import com.enigmacamp.enigshop.repository.RoleRepository;
import com.enigmacamp.enigshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(UserRole role) {

        Optional<Role> optionalRole = roleRepository.findByName(role);

        if(optionalRole.isPresent()) {
            return optionalRole.get();
        }

        Role currentRole = Role.builder()
                .name(role)
                .build();
        return roleRepository.saveAndFlush(currentRole);
    }
}
