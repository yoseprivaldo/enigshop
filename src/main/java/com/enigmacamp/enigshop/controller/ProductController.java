package com.enigmacamp.enigshop.controller;
import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.dto.request.ProductRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.UpdateProductRequest;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.PagingResponse;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.service.ProductService;
import com.enigmacamp.enigshop.utils.validation.PagingUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;


@RestController
@RequestMapping(path = APIUrl.PRODUCT_API)
@Slf4j
public class ProductController {

    ProductService productService;
    ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
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

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateProduct(
            @RequestPart("product") String jsonProduct, // ini tuh bisa menghasilkan  JsonProcessingException
            @RequestPart(name = "images", required = false) List<MultipartFile> images // ini juga
    ){
        try{

            if(jsonProduct == null || jsonProduct.isBlank()){
                return mapToResponseEntity(
                        HttpStatus.BAD_REQUEST,
                        "Format request is not valid",
                        null,
                        null
                );
            }

            UpdateProductRequest request = objectMapper.readValue(jsonProduct, UpdateProductRequest.class);

            request.setImages(Objects.requireNonNullElse(images, Collections.emptyList()));

            ProductResponse productResponse = productService.updatePut(request);

            return mapToResponseEntity(
                    HttpStatus.OK,
                    "success updated data product",
                    productResponse,
                    null
            );

        }  catch (JsonProcessingException e) {
            log.error("JSON parsing error: {}", e.getLocalizedMessage());
            return mapToResponseEntity(
                    HttpStatus.BAD_REQUEST,
                    "Format JSON tidak valid",
                    null,
                    null
            );
        } catch (Exception e){
            log.error("Unexpected error: {}", e.getLocalizedMessage());
            return mapToResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null,
                    null
            );
        }
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
    @PreAuthorize("hasAnyRole(`ADMIN`, `SELLER`)")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String productId){
        productService.deleteById(productId);
        return mapToResponseEntity(
                HttpStatus.OK,
                "success deleted data",
                "OK",
                null
        );
    }

}
