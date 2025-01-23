package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.CommonResponse;
import com.enigmacamp.enigshop.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.dto.response.PagingResponse;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.utils.validation.PagingUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

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

    // METHOD HELPER
    private <T> ResponseEntity<CommonResponse<T>> mapToResponseEntity (
            HttpStatus status,
            String message,
            T data,
            PagingResponse paging

    ){
        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .paging(paging)
                .build();

        return ResponseEntity
                .status(status)
                .header("content-Type", "application/json")
                .body(response);
    }

}
