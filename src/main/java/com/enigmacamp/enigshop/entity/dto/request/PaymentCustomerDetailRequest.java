package com.enigmacamp.enigshop.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentCustomerDetailRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String phone;
    @JsonProperty("shipping_address")
    private ShippingAddress shippingAddress;
}
