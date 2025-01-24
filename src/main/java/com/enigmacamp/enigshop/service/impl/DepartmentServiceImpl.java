package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.dto.request.DepartmentRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.DepartmentResponse;
import com.enigmacamp.enigshop.entity.Department;
import com.enigmacamp.enigshop.repository.DepartmentRepository;
import com.enigmacamp.enigshop.service.DepartmentService;
import com.enigmacamp.enigshop.specification.DepartmentSpecification;
import com.enigmacamp.enigshop.utils.SortingUtil;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse create(DepartmentRequest request) {
        Department department = mapToEntity(request);
        department = departmentRepository.save(department);
        return mapToResponse(department);
    }

    @Override
    public Page<DepartmentResponse> getAll(SearchRequest searchRequest) {
        String fieldName = SortingUtil.sortByValidation(Department.class, searchRequest.getSortBy(), "name");

        searchRequest.setSortBy(fieldName);

        Sort.Direction direction = Sort.Direction.fromString(searchRequest.getDirection());


        Pageable pageable = PageRequest.of(
                searchRequest.getPage(),
                searchRequest.getSize(),
                direction,
                searchRequest.getSortBy()
        );

        Specification<Department> specification = DepartmentSpecification.filterBy(
                searchRequest.getQuery(),
                searchRequest.getCode()
        );


        Page<Department> departmentPage = searchRequest.getQuery() != null && !searchRequest.getQuery().isEmpty()
                ? departmentRepository.findByNameContainingIgnoreCase(searchRequest.getQuery(), pageable)
                : departmentRepository.findAll(specification, pageable);

        if(departmentPage.getContent().isEmpty()){
            throw new ResourcesNotFoundException("Departement not found");
        }

        return departmentPage.map(this::mapToResponse);
    }

    @Override
    public DepartmentResponse getById(Long id) {
        return mapToResponse(getByIdOrThrowException(id));
    }

    private Department getByIdOrThrowException(Long id){
        return departmentRepository.findById(id).orElseThrow(
                () -> new ResourcesNotFoundException("Customer not found")
        );
    }

    @Override
    public DepartmentResponse updatePut(DepartmentRequest request) {
        getByIdOrThrowException(request.getId());
        Department department = mapToEntity(request);
        return mapToResponse(departmentRepository.saveAndFlush(department));
    }

    @Override
    public DepartmentResponse updatePatch(DepartmentRequest request) {
        Department existingDepartment = getByIdOrThrowException(request.getId());

        if(request.getName() != null)  existingDepartment.setName(request.getName());
        if(request.getCode() != null)  existingDepartment.setCode(request.getCode());
        if(request.getDescription() != null ) existingDepartment.setDescription(request.getDescription());

        return mapToResponse(departmentRepository.saveAndFlush(existingDepartment));
    }

    @Override
    public void deleteById(Long id) {
        Department existingDepartment = getByIdOrThrowException(id);
        departmentRepository.delete(existingDepartment);
    }


    public static Department mapToEntity(DepartmentRequest request){
        return Department.builder()
                .id(request.getId())
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
    }

    public DepartmentResponse mapToResponse(Department department){
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .description(department.getDescription())
                .build();
    }
}
