package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.dto.request.ProductRequest;
import com.enigmacamp.enigshop.entity.dto.request.SearchRequest;
import com.enigmacamp.enigshop.entity.dto.request.UpdateProductRequest;
import com.enigmacamp.enigshop.entity.dto.response.ProductResponse;
import com.enigmacamp.enigshop.entity.Image;
import com.enigmacamp.enigshop.entity.Product;
import com.enigmacamp.enigshop.repository.ProductRepository;
import com.enigmacamp.enigshop.service.ImageService;
import com.enigmacamp.enigshop.service.ProductService;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import com.enigmacamp.enigshop.utils.validation.EntityValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    ImageService imageService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Override
    public ProductResponse create(ProductRequest request) {

        EntityValidation.productRequest(request);

        Product product = mapToEntity(request);
        product = productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public Page<ProductResponse> getAll(SearchRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Product> productPage =  request.getQuery() != null && !request.getQuery().isEmpty()
                ? productRepository.findAllBySearch(request.getQuery(), pageable)
                : productRepository.findAll(pageable);

        if(productPage.getContent().isEmpty()){
            throw new ResourcesNotFoundException("Product Not Found");
        }

        return productPage.map(this::mapToResponse);
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = getProductById(id);
        return mapToResponse(product);
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow( () -> new ResourcesNotFoundException("Data tidak ditemukan"));
    }

    @Override
    @Transactional
    public ProductResponse updatePut(UpdateProductRequest request) {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new ResourcesNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());

        if (product.getImages() == null) {
            product.setImages(new ArrayList<>());
        }

        List<File> savedFiles = new ArrayList<>();

        try {
            if(request.getImages() != null && !request.getImages().isEmpty()) {
                for (MultipartFile image : request.getImages()) {
                    log.info("Processing image: {}", image.getOriginalFilename());

                    Image imageToSave = imageService.create(image, "product");

                    imageToSave.setProduct(product);
                    product.getImages().add(imageToSave);

                    log.info("Assigned product ID: {}", product.getId());
                    log.info("Saving image with name: {} and path: {}", imageToSave.getName(), imageToSave.getPath());

                    File savedFile = new File(imageToSave.getPath());
                    savedFiles.add(savedFile);
                }

            } else {
                log.warn("No images provide for the product.");
            }
            // Todo: Services to Save Image
            return mapToResponse(productRepository.saveAndFlush(product));

        }  catch (Exception e){
            for (File file: savedFiles) {
                if(file.exists()){
                    file.delete();
                }
            }
            throw e;
        }
    }

    @Override
    public ProductResponse updatePatch(ProductRequest request) {
        Product existingProduct = getProductById(request.getId());

        if(request.getName() != null) existingProduct.setName(request.getName());
        if(request.getPrice() != null) existingProduct.setPrice(request.getPrice());
        if(request.getDescription() != null) existingProduct.setDescription(request.getDescription());
        if(request.getStock() != null) existingProduct.setStock(request.getStock());

        return mapToResponse(productRepository.saveAndFlush(existingProduct));
    }

    @Override
    public Product updateProduct(Product product) {
        Product existingProduct = getProductById(product.getId());

        if(product.getName() != null) existingProduct.setName(product.getName());
        if(product.getPrice() != null) existingProduct.setPrice(product.getPrice());
        if(product.getDescription() != null) existingProduct.setDescription(product.getDescription());
        if(product.getStock() != null) existingProduct.setStock(product.getStock());

        return existingProduct;
    }

    @Override
    public void deleteById(String id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    public static Product mapToEntity(ProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
