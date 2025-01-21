package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer create(Customer customer);
    List<Customer> getAll(String search);
    Customer getById(String id);
    Customer updatePatch(Customer customer);
    void deleteById(String id);

}
