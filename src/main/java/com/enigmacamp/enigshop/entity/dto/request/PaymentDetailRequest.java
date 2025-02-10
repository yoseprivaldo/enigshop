package com.enigmacamp.enigshop.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentDetailRequest {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("gross_amount")
    private Long grossAmount;


}
