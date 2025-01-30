package com.enigmacamp.enigshop.entity.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingResponse {
    private Integer totalPage;
    private Long totalElement;
    private Integer page;
    private Integer size;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
