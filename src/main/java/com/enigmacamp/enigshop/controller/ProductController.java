package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.dto.response.CommonResponse;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> addNewProduct(@RequestBody ProductRequest request){
        ProductResponse product = productService.create(request);
        return mapToResponseEntity(
                HttpStatus.CREATED,
                "Success create product",
                product
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAll(@RequestParam(name = "search", required = false) String search){
        List<ProductResponse> products = productService.getAll(search);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Product data found",
                products
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String productId){
        ProductResponse product =  productService.getById(productId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Product data not found",
                product
        );
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest request){
        ProductResponse product = productService.updatePut(request);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success updated data",
                product
        );
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<ProductResponse>> update(@RequestBody ProductRequest request){
        ProductResponse product = productService.updatePatch(request);
       return mapToResponseEntity(
               HttpStatus.OK,
               "success updated data",
               product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String productId){
        productService.deleteById(productId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success deleted data",
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
