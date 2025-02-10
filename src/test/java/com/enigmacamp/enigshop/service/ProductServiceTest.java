package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.Product;
import com.enigmacamp.enigshop.entity.dto.request.ProductRequest;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.repository.ProductRepository;
import com.enigmacamp.enigshop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequest request;

    @BeforeEach
    void setUp() {
        request = ProductRequest.builder()
                .name("Test")
                .description("Product description test")
                .price(1000L)
                .stock(3)
                .build();
    }

    @Test
    @DisplayName("Test create product success")
    void name() {
        Product product = Product.builder()
                .id("1234123134")
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name("Test")
                .description("Product description test")
                .price(1000L)
                .stock(3)
                .build();

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        ProductResponse expectResponse = productService.create(request);

        Assertions.assertNotNull(productResponse);
        Assertions.assertNotNull(productResponse.getId());
        Assertions.assertEquals(productResponse, expectResponse);

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    @DisplayName("Test create product fail")
    void createFailed() {
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenThrow(new DataIntegrityViolationException("error"));

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            productService.create(request);
        });

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }
}
