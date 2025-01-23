package com.enigmacamp.enigshop.utils.validation;

import com.enigmacamp.enigshop.dto.request.CustomerRequest;
import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.utils.exception.BadRequestException;

public class EntityValidation {
    public static void productRequest(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new BadRequestException("Product request cannot be null");
        }
        if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            throw new BadRequestException("Product name cannot be null or empty");
        }
        if (productRequest.getPrice() == null || productRequest.getPrice() < 0) {
            throw new BadRequestException("Product price cannot be null or negative");
        }
        if (productRequest.getDescription() == null || productRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Product description cannot be null or empty");
        }
        if (productRequest.getStock() == null || productRequest.getStock() < 0) {
            throw new BadRequestException("Product stock cannot be null or negative");
        }
    }

    public static void customerRequest(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            throw new BadRequestException("Customer request cannot be null");
        }
        if (customerRequest.getFullName() == null || customerRequest.getFullName().isEmpty()) {
            throw new BadRequestException("Customer full name cannot be null or empty");
        }
        if (customerRequest.getEmail() == null || customerRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Customer email cannot be null or empty");
        }
        if (!customerRequest.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new BadRequestException("Invalid email format");
        }
        if (customerRequest.getPhone() == null || customerRequest.getPhone().isEmpty()) {
            throw new BadRequestException("Customer phone cannot be null or empty");
        }
        if (!customerRequest.getPhone().matches("^\\+?[0-9]{10,15}$")) {
            throw new BadRequestException("Invalid phone number format");
        }
        if (customerRequest.getAddress() == null || customerRequest.getAddress().isEmpty()) {
            throw new BadRequestException("Customer address cannot be null or empty");
        }
        if (customerRequest.getIsActive() == null) {
            throw new BadRequestException("Customer isActive status cannot be null");
        }
    }

}
