package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.dto.request.ProductRequest;
import com.enigmacamp.enigshop.dto.request.SearchRequest;
import com.enigmacamp.enigshop.dto.response.CommonResponse;
import com.enigmacamp.enigshop.dto.response.PagingResponse;
import com.enigmacamp.enigshop.dto.response.ProductResponse;
import com.enigmacamp.enigshop.service.ProductService;
import com.enigmacamp.enigshop.utils.validation.PagingUtil;
import org.springframework.data.domain.Page;
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
                product,
                null
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAll(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ){

        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);

        SearchRequest request = SearchRequest.builder()
                .size(size)
                .page(Math.max(page - 1 , 0))
                .query(search)
                .build();

        Page<ProductResponse> products = productService.getAll(request);

        PagingResponse paging = PagingResponse.builder()
                .totalPage(products.getTotalPages())
                .totalElement(products.getTotalElements())
                .page(page)
                .size(size)
                .hasNext(products.hasNext())
                .hasPrevious(products.hasPrevious())
                .build();

        return mapToResponseEntity(
                HttpStatus.OK,
                "Product data found",
                products.getContent(),
                paging
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String productId){
        ProductResponse product =  productService.getById(productId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "Product data found",
                product,
                null
        );
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest request){
        ProductResponse product = productService.updatePut(request);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success updated data",
                product,
                null
        );
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<ProductResponse>> update(@RequestBody ProductRequest request){
        ProductResponse product = productService.updatePatch(request);
       return mapToResponseEntity(
               HttpStatus.OK,
               "success updated data",
               product,
               null
       );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String productId){
        productService.deleteById(productId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success deleted data",
                "OK",
                null
        );
    }

    // METHOD HELPER
    private <T> ResponseEntity<CommonResponse<T>> mapToResponseEntity (
            HttpStatus status,
            String message,
            T data,
            PagingResponse paging){

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
