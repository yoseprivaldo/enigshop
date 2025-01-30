package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
}
