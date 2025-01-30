package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse create(ProductRequest request);
    Page<ProductResponse> getAll(SearchRequest searchRequest);
    ProductResponse getById(String id);
    ProductResponse updatePut(ProductRequest request);
    ProductResponse updatePatch(ProductRequest request);
    void deleteById(String id);
    Product getProductById(String id);
    Product updateProduct(Product product);

}
