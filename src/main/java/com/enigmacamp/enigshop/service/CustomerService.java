package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customer);
    Page<CustomerResponse> getAll(SearchRequest request);
    CustomerResponse getById(String id);
    CustomerResponse updatePatch(CustomerRequest customer);
    void deleteById(String id);

}
