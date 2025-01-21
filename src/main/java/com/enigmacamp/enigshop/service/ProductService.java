package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);
    List<Product> getAll(String name);
    Product getById(String id);
    Product updatePut(Product product);
    Product updatePatch(Product product);
    void deleteById(String id);

}
