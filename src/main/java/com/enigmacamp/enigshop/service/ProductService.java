package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.dto.request.ProductRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.UpdateProductRequest;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse create(ProductRequest request);
    Page<ProductResponse> getAll(SearchRequest searchRequest);
    ProductResponse getById(String id);
    ProductResponse updatePut(UpdateProductRequest request);
    ProductResponse updatePatch(ProductRequest request);
    void deleteById(String id);
    Product getProductById(String id);
    Product updateProduct(Product product);

}
