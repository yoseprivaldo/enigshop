package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.entity.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    Page<TransactionResponse> getAll(SearchRequest searchRequest);
}
