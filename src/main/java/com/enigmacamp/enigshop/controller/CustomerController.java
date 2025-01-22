package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.response.CustomerResponse;
import com.enigmacamp.enigshop.service.CustomerService;
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
    public CustomerResponse addNewCustomer(@RequestBody CustomerRequest request){
        return customerService.create(request);
    }

    @GetMapping
    public List<CustomerResponse> getAll(@RequestParam(name = "search", required = false) String search){
        return customerService.getAll(search);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerById(@PathVariable String customerId){
        return customerService.getById(customerId);
    }

    @PatchMapping
    public CustomerResponse update(@RequestBody CustomerRequest request){
        return customerService.updatePatch(request);
    }

    @DeleteMapping("/{customerId}")
    public String delete(@PathVariable String customerID){
        customerService.deleteById(customerID);
        return "Pelanggan dengan id " + customerID + "  is deleted";
    }

}
