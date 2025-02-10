package com.enigmacamp.enigshop.repository;


import com.enigmacamp.enigshop.entity.Product;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void saveProductSuccess() {

        Product product = Product.builder()
                .name("test")
                .description("test")
                .price(10000L)
                .stock(3)
                .build();

        Product save = productRepository.save(product);

        Product productByEM = entityManager.find(Product.class, save.getId());

        log.info(save.toString());

        Assertions.assertEquals(save, productByEM);
        Assertions.assertNotNull(productByEM.getId());

        Assertions.assertNotNull(save);
        Assertions.assertNotNull(save.getId());
        Assertions.assertEquals(product.getName(), save.getName());
        Assertions.assertEquals(product.getDescription(), save.getDescription());
        Assertions.assertEquals(product.getPrice(), save.getPrice());
        Assertions.assertEquals(product.getStock(), save.getStock());
    }
}
