package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.TransactionRequest;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.dto.response.TransactionDetailResponse;
import com.enigmacamp.enigshop.entity.dto.response.TransactionResponse;
import com.enigmacamp.enigshop.entity.*;
import com.enigmacamp.enigshop.repository.DepartmentRepository;
import com.enigmacamp.enigshop.repository.TransactionDetailRepository;
import com.enigmacamp.enigshop.repository.TransactionRepository;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.service.ProductService;
import com.enigmacamp.enigshop.service.TransactionService;
import com.enigmacamp.enigshop.utils.SortingUtil;
import com.enigmacamp.enigshop.utils.exception.BadRequestException;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import com.enigmacamp.enigshop.utils.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.enigmacamp.enigshop.utils.mapper.CustomerMapper.mapToCustomerResponse;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final DepartmentRepository departmentRepository;

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
                .customer(mapToCustomerResponse(savedTransaction.getCustomer()))
                .date(savedTransaction.getTransactionDate())
                .transactionDetails(listDetailResponse) // Todo: need more mapping entity to response
                .totalPayment(totalPayment.get())
                .build();
    }


    @Override
    public Page<TransactionResponse> getAll(SearchRequest searchRequest) {
        String fieldName = SortingUtil.sortByValidation(Transaction.class, searchRequest.getSortBy(), "id");

        searchRequest.setSortBy(fieldName);

        Sort.Direction direction = Sort.Direction.fromString(searchRequest.getDirection());

        Specification<Transaction> spec = TransactionSpecification.filterByCriteria(
                searchRequest.getQuery(),
                searchRequest.getCode());

        Pageable pageable = PageRequest.of(
                searchRequest.getPage(),
                searchRequest.getSize(),
                direction,
                searchRequest.getSortBy()
        );

        Page<Transaction> transactionPage = transactionRepository.findAll(spec,pageable);

        if (transactionPage.isEmpty()) {
            throw new ResourcesNotFoundException("No transactions found");
        }

        return transactionPage.map(this::mapToTransactionResponse);
    }

    @Override
    public Transaction getById(String transactionId) {
        return transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new ResourcesNotFoundException("No transaction found with id: " + transactionId));
    }

    public TransactionResponse getByIdToResponse(String transactionId) {
        return mapToTransactionResponse(getById(transactionId));
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        List<TransactionDetailResponse> detailResponses = transaction.getTransactionDetails().stream()
                .map(this::mapToDetailTransactionResponses)
                .toList();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customer(mapToCustomerResponse(transaction.getCustomer()))
                .date(transaction.getTransactionDate())
                .transactionDetails(detailResponses)
                .totalPayment(detailResponses.stream()
                    .mapToLong(detail -> detail.getProductPrice() * detail.getQty())
                    .sum())
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
                .product(productResponse)
                .productPrice(transactionDetail.getProductPrice())
                .qty(transactionDetail.getQty())
                .build();
    }

}
