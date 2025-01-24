package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Product;
import com.enigmacamp.enigshop.repository.ProductRepository;
import com.enigmacamp.enigshop.service.ProductService;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import com.enigmacamp.enigshop.utils.validation.EntityValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {

        EntityValidation.productRequest(request);

        Product product = mapToEntity(request);
        product = productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public Page<ProductResponse> getAll(SearchRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Product> productPage =  request.getQuery() != null && !request.getQuery().isEmpty()
                ? productRepository.findAllBySearch(request.getQuery(), pageable)
                : productRepository.findAll(pageable);

        if(productPage.getContent().isEmpty()){
            throw new ResourcesNotFoundException("Product Not Found");
        }

        return productPage.map(this::mapToResponse);
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = getProductById(id);
        return mapToResponse(product);
    }

    private Product getProductById(String id){
        return productRepository.findById(id).orElseThrow( () -> new ResourcesNotFoundException("Data tidak ditemukan"));
    }

    @Override
    public ProductResponse updatePut(ProductRequest request) {
        EntityValidation.productRequest(request);

        getProductById(request.getId());
        Product product = mapToEntity(request);
        return mapToResponse(productRepository.saveAndFlush(product));
    }


    @Override
    public ProductResponse updatePatch(ProductRequest request) {
        Product existingProduct = getProductById(request.getId());

        if(request.getName() != null) existingProduct.setName(request.getName());
        if(request.getPrice() != null) existingProduct.setPrice(request.getPrice());
        if(request.getDescription() != null) existingProduct.setDescription(request.getDescription());
        if(request.getStock() != null) existingProduct.setStock(request.getStock());

        return mapToResponse(productRepository.saveAndFlush(existingProduct));
    }

    @Override
    public void deleteById(String id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    public static Product mapToEntity(ProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
