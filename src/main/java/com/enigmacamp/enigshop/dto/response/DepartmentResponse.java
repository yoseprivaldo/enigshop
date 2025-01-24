package com.enigmacamp.enigshop.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
}
