package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.entity.Customer;
import com.enigmacamp.enigshop.repository.CustomerRepository;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import com.enigmacamp.enigshop.utils.validation.EntityValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {

        EntityValidation.customerRequest(request);

        Customer customer = mapToEntity(request);
        customer = customerRepository.save(customer);
        return mapToResponse(customer);

    }

    @Override
    public Page<CustomerResponse> getAll(SearchRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Customer> customerPage = request.getQuery()!= null && !request.getQuery().isEmpty()
                ? customerRepository.findByFullNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                        request.getQuery(), request.getQuery(), pageable)
                : customerRepository.findAll(pageable);

        if(customerPage.isEmpty()){
            throw new ResourcesNotFoundException("Customer not found");
        }

        return customerPage.map(this::mapToResponse);
    }

    @Override
    public CustomerResponse getById(String id) {
        return mapToResponse(getByIdAndThrowException(id));
    }

    public Customer getByIdAndThrowException(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public CustomerResponse updatePatch(CustomerRequest request) {
        Customer existingCustomer = getByIdAndThrowException(request.getId());

        if(request.getFullName() != null) existingCustomer.setFullName(request.getFullName());
        if(request.getEmail() != null) existingCustomer.setEmail(request.getEmail());
        if(request.getAddress() != null) existingCustomer.setAddress(request.getAddress());
        if(request.getPhone() != null) existingCustomer.setPhone(request.getPhone());
        if(request.getIsActive() != null) existingCustomer.setIsActive(request.getIsActive());

        return mapToResponse(customerRepository.saveAndFlush(existingCustomer));
    }

    @Override
    public void deleteById(String id) {
        Customer customerExisting = getByIdAndThrowException(id);
        customerRepository.delete(customerExisting);
    }


    public static Customer mapToEntity(CustomerRequest request){
        return Customer.builder()
                .id(request.getId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .isActive(request.getIsActive())
                .build();
    }

    public CustomerResponse mapToResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .isActive(customer.getIsActive())
                .build();
    }
}
