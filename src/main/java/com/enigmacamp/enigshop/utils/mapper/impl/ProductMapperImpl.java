package com.enigmacamp.enigshop.utils.mapper.impl;

import com.enigmacamp.enigshop.entity.dto.request.ProductRequest;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Product;
import com.enigmacamp.enigshop.utils.mapper.ProductMapper;

public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product requestToEntity(ProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

    }

    @Override
    public ProductResponse entityToResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .build();
    }
}
