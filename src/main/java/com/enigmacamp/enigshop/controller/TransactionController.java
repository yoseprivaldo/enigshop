package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.dto.response.CommonResponse;
import com.enigmacamp.enigshop.dto.response.PagingResponse;
import com.enigmacamp.enigshop.dto.response.TransactionResponse;
import com.enigmacamp.enigshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.TRANSACTION_API)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createNewTransaction(@RequestBody TransactionRequest request){
        TransactionResponse transactionResponse = transactionService.create(request);

        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Success create transactions",
                transactionResponse,
                null
        );
    }


    // METHOD HELPER
    private <T> ResponseEntity<CommonResponse<T>> mapToResponseEntity (
            HttpStatus status,
            String message,
            T data,
            PagingResponse paging){

        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .paging(paging)
                .build();

        return ResponseEntity
                .status(status)
                .header("content-Type", "application/json")
                .body(response);
    }



}
