package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.Product;
import com.enigmacamp.enigshop.repository.ProductRepository;
import com.enigmacamp.enigshop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll(String search) {
        return productRepository.findAllBySearch(search);
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow( () -> new RuntimeException("Data tidak ditemukan"));
    }

    @Override
    public Product updatePut(Product product) {
        // Todo: Ceck Data Available On DB
        Product existingProduct = getById(product.getId());

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setStock(product.getStock());
        return productRepository.saveAndFlush(existingProduct);
    }

    @Override
    public Product updatePatch(Product product) {
        // Todo: Ceck Data Available On DB
        Product existingProduct = getById(product.getId());

        // Todo: Check field want to update
        if(product.getName() != null) existingProduct.setName(product.getName());
        if(product.getPrice() != null) existingProduct.setPrice(product.getPrice());
        if(product.getDescription() != null) existingProduct.setDescription(product.getDescription());
        if(product.getStock() != null) existingProduct.setStock(product.getStock());

        return productRepository.saveAndFlush(existingProduct);
    }

    @Override
    public void deleteById(String id) {
        Product existingProduct = getById(id);
        productRepository.delete(existingProduct);
    }
}
