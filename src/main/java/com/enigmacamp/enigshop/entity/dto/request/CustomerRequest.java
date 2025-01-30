package com.enigmacamp.enigshop.entity.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRequest {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;
}
