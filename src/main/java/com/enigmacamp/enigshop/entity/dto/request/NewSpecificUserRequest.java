package com.enigmacamp.enigshop.entity.dto.request;

import com.enigmacamp.enigshop.constant.UserRole;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewSpecificUserRequest {
    private String username;
    private String password;
    private List<UserRole> roles;
}
