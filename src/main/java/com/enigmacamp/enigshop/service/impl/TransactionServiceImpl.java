package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.dto.response.TransactionDetailResponse;
import com.enigmacamp.enigshop.dto.response.TransactionResponse;
import com.enigmacamp.enigshop.entity.Customer;
import com.enigmacamp.enigshop.entity.Product;
import com.enigmacamp.enigshop.entity.Transaction;
import com.enigmacamp.enigshop.entity.TransactionDetail;
import com.enigmacamp.enigshop.repository.TransactionDetailRepository;
import com.enigmacamp.enigshop.repository.TransactionRepository;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.service.ProductService;
import com.enigmacamp.enigshop.service.TransactionService;
import com.enigmacamp.enigshop.utils.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest request) {
        Customer customer = customerService.getByIdAndThrowException(request.getCustomerId());
        Date currentDate = new Date();

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDate(currentDate)
                .build();

        AtomicReference<Long> totalPayment = new AtomicReference<>(0L);
        List<TransactionDetailResponse> listDetailResponse = new ArrayList<>();

        List<TransactionDetail> list = request.getTransactionDetail().stream().map(detailRequest -> {
            Product product = productService.getProductById(detailRequest.getProductId());

            // TODO: Make sure quantity request less or equal than stock product
            if (product.getStock() < detailRequest.getQty()) {
                throw new BadRequestException("Quantity requested exceeds available stock for product: " + product.getName());
            }

            // TODO: Update stock Product in Entity
            product.setStock(product.getStock() - detailRequest.getQty());

            productService.updateProduct(product);

            TransactionDetail trx_detail = TransactionDetail.builder()
                    .product(product)
                    .transaction(transaction)
                    .qty(detailRequest.getQty())
                    .productPrice(product.getPrice())
                    .build();

            totalPayment.updateAndGet(v -> v + product.getPrice() * detailRequest.getQty());

            transactionDetailRepository.save(trx_detail);

            listDetailResponse.add(mapToDetailTransactionResponses(trx_detail));

            return trx_detail;

        }).toList();

        transaction.setTransactionDetails(list);

        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .customer(savedTransaction.getCustomer())
                .date(savedTransaction.getTransactionDate())
                .transactionDetails(listDetailResponse) // Todo: need more mapping entity to response
                .totalPayment(totalPayment.get())
                .build();
    }

    private TransactionDetailResponse mapToDetailTransactionResponses(TransactionDetail transactionDetail){
        ProductResponse productResponse = ProductResponse.builder()
                .id(transactionDetail.getProduct().getId())
                .name(transactionDetail.getProduct().getName())
                .price(transactionDetail.getProductPrice())
                .description(transactionDetail.getProduct().getDescription())
                .stock(transactionDetail.getProduct().getStock())
                .build();

        return TransactionDetailResponse.builder()
                .id(transactionDetail.getId())
                .productResponse(productResponse)
                .productPrice(transactionDetail.getProductPrice())
                .qty(transactionDetail.getQty())
                .build();
    }

}
