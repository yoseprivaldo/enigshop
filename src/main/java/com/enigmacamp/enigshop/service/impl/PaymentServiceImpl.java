package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.constant.TransactionStatus;
import com.enigmacamp.enigshop.entity.Payment;
import com.enigmacamp.enigshop.entity.Transaction;
import com.enigmacamp.enigshop.entity.dto.request.*;
import com.enigmacamp.enigshop.entity.dto.response.TransactionResponse;
import com.enigmacamp.enigshop.repository.PaymentRepository;
import com.enigmacamp.enigshop.repository.TransactionRepository;
import com.enigmacamp.enigshop.service.PaymentService;
import com.enigmacamp.enigshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionService transactionService;
    private final RestClient restClient;

    @Value("${payment.secret-key}")
    private String SECRET_KEY;

    @Value("${payment.url}")
    private String PAYMENT_URL;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment createPayment(String transactionId) {
        // Menampilkan validasi id transaction
        TransactionResponse transactionResponse = transactionService.getByIdToResponse(transactionId);

        // TODO: Create Payment request
        PaymentRequest request = PaymentRequest.builder()
                .paymentDetail(
                        PaymentDetailRequest.builder()
                                .orderId("Enigshop-123")
                                .grossAmount(transactionResponse.getTotalPayment())
                                .build()
                )
                .itemDetails(List.of())
                .customerDetails(PaymentCustomerDetailRequest.builder()
                        .firstName(transactionResponse.getCustomer().getFullName())
                        .lastName(transactionResponse.getCustomer().getFullName())
                        .email(transactionResponse.getCustomer().getEmail())
                        .phone(transactionResponse.getCustomer().getPhone())
                        .shippingAddress(
                                ShippingAddress.builder()
                                        .firstName(transactionResponse.getCustomer().getFullName())
                                        .lastName(transactionResponse.getCustomer().getFullName())
                                        .phone(transactionResponse.getCustomer().getPhone())
                                        .email(transactionResponse.getCustomer().getEmail())
                                        .address(transactionResponse.getCustomer().getAddress())
                                        .build()
                        )
                        .build())
                .enabledPayments(List.of("bni_va","akulaku", "shopeepay"))
                .pageExpiry(PaymentPageExpiryRequest.builder()
                        .duration(1)
                        .unit("hours")
                        .build())
                .build();

        // TODO: Payment request
        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(PAYMENT_URL)
                .body(request)
                .header("Authorization", "Basic " + SECRET_KEY)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        Map<String, String> body = response.getBody();

        // TODO Save Payment Data
        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUri(body.get("redirect_url"))
                .transactionStatus(TransactionStatus.ORDERED)
                .build();

        paymentRepository.saveAndFlush(payment);
        return payment;
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}

