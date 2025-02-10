package com.enigmacamp.enigshop.utils.mapper;

import com.enigmacamp.enigshop.entity.Customer;
import com.enigmacamp.enigshop.entity.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.entity.dto.response.CustomerResponse;

public class CustomerMapper {
    public static Customer mapToCustomerEntity(CustomerRequest request){
        return Customer.builder()
                .id(request.getId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .build();
    }

    public static CustomerResponse mapToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }

    public static CustomerRequest mapToCustomerRequest(Customer customer) {
        return CustomerRequest.builder()
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .birthDate(customer.getBirthDate())
                .build();
    }
}
