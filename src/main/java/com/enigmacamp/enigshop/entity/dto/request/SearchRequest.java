package com.enigmacamp.enigshop.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String query;
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String code;
}
