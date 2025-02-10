package com.enigmacamp.enigshop.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentRequest {
    @JsonProperty("transaction_details")
    private PaymentDetailRequest paymentDetail;

    @JsonProperty("item_details")
    private List<PaymentItemDetailRequest> itemDetails;

    @JsonProperty("customer_details")
    private PaymentCustomerDetailRequest customerDetails;

    @JsonProperty("enabled_payments")
    private List<String> enabledPayments;

    @JsonProperty("page_expiry")
    private PaymentPageExpiryRequest pageExpiry;
}

