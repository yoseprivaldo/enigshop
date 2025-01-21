package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.Customer;
import com.enigmacamp.enigshop.repository.CustomerRepository;
import com.enigmacamp.enigshop.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll(String search) {
        return customerRepository.findAllBySearch(search);
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer tidak ditemukan"));
    }

    @Override
    public Customer updatePatch(Customer customer) {
        Customer existingCustomer = getById(customer.getId());

        // Todo: Check field want to update
        if(customer.getFullName() != null) existingCustomer.setFullName(customer.getFullName());
        if(customer.getEmail() != null) existingCustomer.setEmail(customer.getEmail());
        if(customer.getAddress() != null) existingCustomer.setAddress(customer.getAddress());
        if(customer.getPhone() != null) existingCustomer.setPhone(customer.getPhone());
        if(customer.getIsActive() != null) existingCustomer.setIsActive(customer.getIsActive());

        return customerRepository.saveAndFlush(existingCustomer);
    }

    @Override
    public void deleteById(String id) {
        Customer customerExisting = getById(id);
        customerRepository.delete(customerExisting);
    }
}
