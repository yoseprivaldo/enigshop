package com.enigmacamp.enigshop.entity.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
}
