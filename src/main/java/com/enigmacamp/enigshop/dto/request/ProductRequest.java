package com.enigmacamp.enigshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
}
