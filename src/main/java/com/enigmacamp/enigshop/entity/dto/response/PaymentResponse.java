package com.enigmacamp.enigshop.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String token;
    @JsonProperty("redirect_url")
    private String redirectUrl;
}


