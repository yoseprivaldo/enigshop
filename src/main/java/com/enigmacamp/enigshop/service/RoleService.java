package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.constant.UserRole;
import com.enigmacamp.enigshop.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
