package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    @Query(
            "SELECT c FROM Customer c WHERE " +
                    "(:search IS NULL OR LOWER(CAST(c.fullName AS string)) LIKE CONCAT('%', :search, '%') " +
                    "OR LOWER(CAST(c.address AS string)) LIKE CONCAT('%', :search, '%'))"
    )
    Page<Customer> findAllBySearch(@Param("search") String search, Pageable pageable);

    Page<Customer> findByFullNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String fullName, String address, Pageable pageable);

}
