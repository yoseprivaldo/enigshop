package com.enigmacamp.enigshop.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Customer {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;

    public Customer(String fullName, String email, String phone, String address, Boolean isActive){
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.phone= phone;
        this.address= address;
        this.isActive= isActive;
    }
}
