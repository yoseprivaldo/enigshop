package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    // tidak ada
}
