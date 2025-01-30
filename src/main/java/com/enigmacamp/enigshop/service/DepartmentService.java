package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.dto.request.DepartmentRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.response.DepartmentResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    DepartmentResponse create(DepartmentRequest request);
    Page<DepartmentResponse> getAll(SearchRequest searchRequest);
    DepartmentResponse getById(Long id);
    DepartmentResponse updatePut(DepartmentRequest request);
    DepartmentResponse updatePatch(DepartmentRequest request);
    void deleteById(Long id);

}
