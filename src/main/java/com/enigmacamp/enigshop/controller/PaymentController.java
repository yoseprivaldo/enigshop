package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.Payment;
import com.enigmacamp.enigshop.entity.Transaction;
import com.enigmacamp.enigshop.entity.dto.request.PaymentRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.PaymentResponse;
import com.enigmacamp.enigshop.entity.dto.response.TransactionResponse;
import com.enigmacamp.enigshop.service.PaymentService;
import com.enigmacamp.enigshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(APIUrl.PAYMENT_API)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<CommonResponse<PaymentResponse>> payment(@RequestBody String  transactionId) {
        Transaction transaction = transactionService.getById(transactionId);

        Payment payment = paymentService.createPayment(transactionId);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUri())
                .build();

        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Success Ordered Payment",
                paymentResponse,
                null
        );

    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<PaymentResponse>>> getAllPayments() {
        List<Payment> listPayment = paymentService.getAll();

        List<PaymentResponse> listPaymentResponse = listPayment.stream().map(
                payment -> PaymentResponse.builder()
                        .token(payment.getToken())
                        .redirectUrl(payment.getRedirectUri())
                        .build()).toList();

        return mapToResponseEntity(
                HttpStatus.OK,
                "Success retrieved payments",
                listPaymentResponse,
                null
        );
    }

}
