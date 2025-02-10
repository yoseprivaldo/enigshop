package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
