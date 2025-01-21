package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.Product;
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
    public Product addNewProduct(@RequestBody Product product){
        return productService.create(product);
    }

    @GetMapping
    public List<Product> getAll(@RequestParam(name = "search", required = false) String search){
        return productService.getAll(search);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId){
        return productService.getById(productId);
    }

//    @GetMapping("/{productId}")
//    public Product getProductByName(@PathVariable String productName){
//        Optional<Product> searchProduct = products.stream().filter(product -> product.getName().equalsIgnoreCase(productName)).findFirst();
//
//        return searchProduct.orElseThrow(() -> new RuntimeException("Product Not Found"));
//    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product){
        return productService.updatePut(product);
    }

    @PatchMapping
    public Product update(@RequestBody Product product){
       return productService.updatePatch(product);
    }

    @DeleteMapping("/{productId}")
    public String delete(@PathVariable String productId){
        productService.deleteById(productId);
        return "Product with Id: " + productId + " is deleted";
    }

}
