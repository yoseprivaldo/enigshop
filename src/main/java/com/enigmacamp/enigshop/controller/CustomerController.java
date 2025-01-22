package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.response.CommonResponse;
import com.enigmacamp.enigshop.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.utils.validation.Validation;
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
        // validasi request
        Validation.customerRequest(request);

        CustomerResponse customer = customerService.create(request);
        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Pelanggan berhasil dibuat",
                customer
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAll(@RequestParam(name = "search", required = false) String search){
        List<CustomerResponse> customers = customerService.getAll(search);

        return mapToResponseEntity(
                HttpStatus.OK,
                "Data pelanggan ditemukan",
                customers
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String customerId){
        CustomerResponse customer = customerService.getById(customerId);

        return mapToResponseEntity(
                HttpStatus.OK,
                "Data customer ditemukan",
                customer
        );
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> update(@RequestBody CustomerRequest request){
        CustomerResponse customer = customerService.updatePatch(request);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Data berhasil di update",
                customer
        );
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String customerID){
        customerService.deleteById(customerID);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Data berhasil dihapus",
                "OK"
        );
    }

    // METHOD HELPER
    private <T> ResponseEntity<CommonResponse<T>> mapToResponseEntity (HttpStatus status, String message, T data){
        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity
                .status(status)
                .header("content-Type", "application/json")
                .body(response);
    }

}
