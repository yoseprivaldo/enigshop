package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.Payment;
import com.enigmacamp.enigshop.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    Payment createPayment(String transactionId);
    List<Payment> getAll();
}
