package com.enigmacamp.enigshop.utils.mapper;

import com.enigmacamp.enigshop.entity.dto.request.ProductRequest;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Product;

public interface ProductMapper {
    Product requestToEntity(ProductRequest request);
    ProductResponse entityToResponse(Product entity);
}
