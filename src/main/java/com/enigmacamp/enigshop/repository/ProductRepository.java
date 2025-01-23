package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(
            "SELECT p FROM Product p WHERE " +
                    "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))"
    )
    Page<Product> findAllBySearch(@Param("search") String search, Pageable pageable);

}


