package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private List<Product> products= new ArrayList<>();

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){

        Product newProduct = new Product(product.getName(), product.getPrice(), product.getStock());

        products.add(product);
        return newProduct;
    }

    @GetMapping
    public List<Product> getAll(@RequestParam(name = "search", required = false) String search){
        if(search != null){
            return products.stream().filter(product -> product.getName().contains(search)).toList();
        }
        return products;
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable UUID productId){
        for (Product product : products) {
            if(product.getId().equals(productId)){
                return product;
            }
        }
        return null;
    }


    @PutMapping
    public List<Product> updateALl(@RequestBody Product product){
        Product foundProduct = getProductById(product.getId());
        int index = products.indexOf(foundProduct);

        if(foundProduct!= null){
            products.set(index, foundProduct);
        }

        return products;
    }

    @PatchMapping
    public List<Product> update(@RequestBody Product payload){
       Product product =  getProductById(payload.getId());

       if(product != null){
          if(payload.getName() != null){
              product.setName(payload.getName());
          }
          if(payload.getPrice() != null){
              product.setPrice(payload.getPrice());
          }
          if(payload.getStock() != null){
              product.setStock(payload.getStock());
          }
       }

       return products;
    }

    @DeleteMapping("/{productId}")
    public String delete(@PathVariable UUID productId){
        products.remove(getProductById(productId));
        return "Product dengan id " + productId + "  berhasil dihapus";
    }

}
