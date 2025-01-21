package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.entity.Customer;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    private List<Customer> customers = new ArrayList<>();

    @PostMapping
    public Customer addNewCustomer(@RequestBody Customer customer){
        Customer newCustomer = new Customer(
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getIsActive());

        customers.add(newCustomer);

        return newCustomer;
    }

    @GetMapping
    public List<Customer> getAll(@RequestParam(name = "search", required = false) String search){
        if(search != null){
            return customers.stream().filter(customer -> customer.getFullName().contains(search)).toList();
        }
        return customers;
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable UUID customerId){
        for (Customer customer : customers) {
            if(customer.getId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }

    @PatchMapping
    public Customer update(@RequestBody Customer payload){
        Customer customer =  getCustomerById(payload.getId());
        if(customer != null){
            if(payload.getFullName() != null){
                customer.setFullName(payload.getFullName());
            }
            if(payload.getEmail() != null){
                customer.setEmail(payload.getEmail());
            }
            if(payload.getPhone() != null){
                customer.setPhone(payload.getPhone());
            }
            if(payload.getIsActive() != null){
                customer.setIsActive(payload.getIsActive());
            }
        }

        return customer;
    }

    @DeleteMapping("/{customerId}")
    public String delete(@PathVariable UUID customerID){
        customers.remove(getCustomerById(customerID));
        return "Pelanggan dengan id " + customerID + "  is deleted";
    }

}
