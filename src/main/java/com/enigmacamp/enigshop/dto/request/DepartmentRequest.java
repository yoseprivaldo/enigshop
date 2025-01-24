package com.enigmacamp.enigshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {
    private Long id;
    private String name;
    private String code;
    private String description;
}
