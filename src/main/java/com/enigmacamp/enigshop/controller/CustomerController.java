package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.UpdateCustomerRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.entity.dto.response.PagingResponse;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.utils.validation.PagingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(path = APIUrl.CUSTOMER_API)
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> addNewCustomer(@RequestBody CustomerRequest request){


        CustomerResponse customer = customerService.create(request);
        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Pelanggan berhasil dibuat",
                customer,
                null
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAll(
            @RequestParam(name = "search", required = false) String search,
             @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ){
        // Validasi Param
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);

        SearchRequest request = SearchRequest.builder()
                .size(size)
                .page(Math.max(page-1, 0))
                .query(search)
                .build();

        Page<CustomerResponse> customers = customerService.getAll(request);

        PagingResponse paging = PagingResponse.builder()
                .totalPage(customers.getTotalPages())
                .totalElement(customers.getTotalElements())
                .page(page)
                .size(size)
                .hasNext(customers.hasNext())
                .hasPrevious(customers.hasPrevious())
                .build();

        return mapToResponseEntity(
                HttpStatus.OK,
                "Data pelanggan ditemukan",
                customers.getContent(),
                paging
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String customerId){
        CustomerResponse customer = customerService.getById(customerId);

        return mapToResponseEntity(
                HttpStatus.OK,
                "Data customer ditemukan",
                customer,
                null
        );
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateCustomer(
            @RequestPart(name = "customer") String jsonCustomer,
            @RequestPart(name = "image") MultipartFile image
            )  {

        try {
            UpdateCustomerRequest request = objectMapper.readValue(jsonCustomer, UpdateCustomerRequest.class);
            request.setImage(image);

            CustomerResponse customerResponse = customerService.updatePut(request);

            return mapToResponseEntity(
                    HttpStatus.OK,
                    "Customer with Id " + request.getId() + " updated successfully.",
                    customerResponse,
                    null
            );
        } catch (Exception e) {
            log.error("error: {} ", e.getLocalizedMessage());
            return mapToResponseEntity(
                    HttpStatus.BAD_REQUEST,
                    "Mohon maaf, terjadi kesalahan request",
                    null,
                    null
            );
        }

    }

    @PatchMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> update(@RequestBody CustomerRequest request){
        CustomerResponse customer = customerService.updatePatch(request);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Data berhasil di update",
                customer,
                null
        );
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String customerID){
        customerService.deleteById(customerID);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Data berhasil dihapus",
                "OK",
                null
        );
    }



}
