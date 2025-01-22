package com.enigmacamp.enigshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;
}
