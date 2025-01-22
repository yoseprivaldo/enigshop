package com.enigmacamp.enigshop.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerResponse {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;
}
