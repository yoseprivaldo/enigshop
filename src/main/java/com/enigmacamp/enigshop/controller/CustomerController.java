package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.Customer;
import com.enigmacamp.enigshop.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer addNewCustomer(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping
    public List<Customer> getAll(@RequestParam(name = "search", required = false) String search){
        return customerService.getAll(search);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId){
        return customerService.getById(customerId);
    }

    @PatchMapping
    public Customer update(@RequestBody Customer payload){
        Customer customerExisting = customerService.getById(payload.getId());

        // Todo: Check field want to update
        if(payload.getFullName() != null) customerExisting.setFullName(payload.getFullName());
        if(payload.getEmail() != null) customerExisting.setEmail(payload.getEmail());
        if(payload.getAddress() != null) customerExisting.setAddress(payload.getAddress());
        if(payload.getPhone() != null) customerExisting.setPhone(payload.getPhone());
        if(payload.getIsActive() != null) customerExisting.setIsActive(payload.getIsActive());

        return customerService.updatePatch(customerExisting);

    }

    @DeleteMapping("/{customerId}")
    public String delete(@PathVariable String customerID){
        customerService.deleteById(customerID);
        return "Pelanggan dengan id " + customerID + "  is deleted";
    }

}
