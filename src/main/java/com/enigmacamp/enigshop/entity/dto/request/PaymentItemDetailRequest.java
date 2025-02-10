package com.enigmacamp.enigshop.entity.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentItemDetailRequest {
    private String id;
    private int price;
    private int quantity;
    private String name;
}
