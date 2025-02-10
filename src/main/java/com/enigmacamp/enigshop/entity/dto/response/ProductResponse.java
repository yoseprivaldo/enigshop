package com.enigmacamp.enigshop.entity.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private Long price;
    private String description;
    private Integer stock;
}
