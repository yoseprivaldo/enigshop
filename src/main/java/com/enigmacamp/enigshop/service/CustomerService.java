package com.enigmacamp.enigshop.service;
import com.enigmacamp.enigshop.entity.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.UpdateCustomerRequest;
import com.enigmacamp.enigshop.entity.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customer);
    Page<CustomerResponse> getAll(SearchRequest request);
    CustomerResponse getById(String id);
    CustomerResponse updatePatch(CustomerRequest customer);
    CustomerResponse updatePut(UpdateCustomerRequest request);
    void deleteById(String id);
    Customer getByIdAndThrowException(String id);

}
