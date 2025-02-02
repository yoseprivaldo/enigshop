package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.dto.request.DepartmentRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.DepartmentResponse;
import com.enigmacamp.enigshop.entity.dto.response.PagingResponse;
import com.enigmacamp.enigshop.service.DepartmentService;
import com.enigmacamp.enigshop.utils.validation.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(path = APIUrl.DEPARTMENT_API)
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<CommonResponse<DepartmentResponse>> addNewDepartment(@RequestBody DepartmentRequest request){
        DepartmentResponse product = departmentService.create(request);

        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Success create department",
                product,
                null
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<DepartmentResponse>>> getAll(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name= "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name= "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name= "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", required = false, defaultValue = "name") String sortBy
    ){
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);


        SearchRequest searchRequest = SearchRequest.builder()
                .query(search)
                .code(code)
                .page(Math.max(page -1 , 0))
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .build();

        Page<DepartmentResponse> departments = departmentService.getAll(searchRequest);

        PagingResponse paging = PagingResponse.builder()
                .totalPage(departments.getTotalPages())
                .totalElement(departments.getTotalElements())
                .page(page)
                .size(size)
                .hasNext(departments.hasNext())
                .hasPrevious(departments.hasPrevious())
                .build();

        return mapToResponseEntity(
                HttpStatus.OK,
                "Department data found",
                departments.getContent(),
                paging
        );

    }


    @GetMapping("/{departmentId}")
    public ResponseEntity<CommonResponse<DepartmentResponse>> getProductById(@PathVariable Long departmentId){
        DepartmentResponse product =  departmentService.getById(departmentId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Department data found",
                product,
                null
        );
    }

    @PutMapping
    public ResponseEntity<CommonResponse<DepartmentResponse>> updateProduct(@RequestBody DepartmentRequest request){
        DepartmentResponse product = departmentService.updatePut(request);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success updated data",
                product,
                null
        );
    }


    @PatchMapping
    public ResponseEntity<CommonResponse<DepartmentResponse>> update(@RequestBody DepartmentRequest request){
        DepartmentResponse product = departmentService.updatePatch(request);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success updated data",
                product,
                null
        );
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable Long departmentId){
        departmentService.deleteById(departmentId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success deleted data",
                "OK",
                null
        );
    }

}
