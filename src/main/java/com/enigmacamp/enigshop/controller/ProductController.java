package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.service.ProductService;
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
    public ProductResponse addNewProduct(@RequestBody ProductRequest request){
        return productService.create(request);
    }

    @GetMapping
    public List<ProductResponse> getAll(@RequestParam(name = "search", required = false) String search){
        return productService.getAll(search);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable String productId){
        return productService.getById(productId);
    }

    @PutMapping
    public ProductResponse updateProduct(@RequestBody ProductRequest product){
        return productService.updatePut(product);
    }

    @PatchMapping
    public ProductResponse update(@RequestBody ProductRequest product){
       return productService.updatePatch(product);
    }

    @DeleteMapping("/{productId}")
    public String delete(@PathVariable String productId){
        productService.deleteById(productId);
        return "Product with Id: " + productId + " is deleted";
    }

}
