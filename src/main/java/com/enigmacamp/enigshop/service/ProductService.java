package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Product;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);
    List<ProductResponse> getAll(String name);
    ProductResponse getById(String id);
    ProductResponse updatePut(ProductRequest request);
    ProductResponse updatePatch(ProductRequest request);
    void deleteById(String id);

}
