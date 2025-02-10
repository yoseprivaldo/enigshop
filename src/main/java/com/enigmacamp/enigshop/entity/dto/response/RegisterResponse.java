package com.enigmacamp.enigshop.entity.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String userId;
    private List<String> roles;
}
