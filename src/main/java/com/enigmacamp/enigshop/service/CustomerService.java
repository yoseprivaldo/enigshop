package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customer);
    List<CustomerResponse> getAll(String search);
    CustomerResponse getById(String id);
    CustomerResponse updatePatch(CustomerRequest customer);
    void deleteById(String id);

}
