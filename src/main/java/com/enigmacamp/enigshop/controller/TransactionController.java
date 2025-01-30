package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.PagingResponse;
import com.enigmacamp.enigshop.entity.dto.response.TransactionResponse;
import com.enigmacamp.enigshop.service.TransactionService;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransactions(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String direction,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String code ){

        SearchRequest searchRequest = SearchRequest.builder()
                .page(Math.max(page-1, 0))
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .query(query)
                .code(code)
                .build();

        Page<TransactionResponse> transactions = transactionService.getAll(searchRequest);

        if(transactions.isEmpty()) throw new ResourcesNotFoundException("Transactions Data Not Found");

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(transactions.getTotalPages())
                .totalElement(transactions.getTotalElements())
                .page(transactions.getNumber())
                .size(transactions.getSize())
                .hasNext(transactions.hasNext())
                .hasPrevious(transactions.hasPrevious())
                .build();

        return mapToResponseEntity(
                HttpStatus.OK,
                "Success fetch transactions",
                transactions.getContent(),
                pagingResponse
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
