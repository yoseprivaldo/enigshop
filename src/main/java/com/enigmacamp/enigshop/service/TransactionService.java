package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.Transaction;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.entity.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    Transaction getById(String transactionId);
    Page<TransactionResponse> getAll(SearchRequest searchRequest);
    TransactionResponse getByIdToResponse(String transactionId);
}
