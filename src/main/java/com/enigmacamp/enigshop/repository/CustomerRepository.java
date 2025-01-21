package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {


    @Query(
            "SELECT c from Customer c WHERE" +
                    "(:search IS NULL OR LOWER(c.fullName) LIKE(CONCAT('%', :search, '%'))" +
                    "OR LOWER(c.address) LIKE LOWER(CONCAT('%', :search, '%')))"
    )
    List<Customer> findAllBySearch(@Param("search") String search);

}
