package com.enigmacamp.enigshop.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private Long price;
    private String description;
    private Integer stock;
}
