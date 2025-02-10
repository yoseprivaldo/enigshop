package com.enigmacamp.enigshop.entity.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentPageExpiryRequest {
    private int duration;
    private String unit;
}
