package com.enigmacamp.enigshop.controller;

import com.enigmacamp.enigshop.constant.APIUrl;
import com.enigmacamp.enigshop.entity.Image;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.repository.CustomerRepository;
import com.enigmacamp.enigshop.service.CustomerService;
import com.enigmacamp.enigshop.service.ImageService;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(APIUrl.IMAGE_API)
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @GetMapping("customer/{customerId}")
    public ResponseEntity<byte[]> getCustomerImage(@PathVariable String customerId) {
        Image image = imageService.getCustomerImage(customerId);


        if(image == null) {
            throw new ResourcesNotFoundException("Image Customer Not Found");
        }

        File file = new File(image.getPath());

        if(!file.exists()){
            throw new ResourcesNotFoundException("Image file not found at path");
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());

            return ResponseEntity.ok()
                    .header("Content-Type", "image/png")
                    .body(imageBytes);


        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("product/{productId}")
    public ResponseEntity<List<byte[]>> getProductImages(@PathVariable String productId) {
        List<Image> productImages = imageService.getProductImages(productId);

        if (productImages.isEmpty()) {
            throw new ResourcesNotFoundException("No images found for the product");
        }

        List<byte[]> imageBytesList = new ArrayList<>();

        for (Image image : productImages) {
            File file = new File(image.getPath());

            if (!file.exists()) {
                log.warn("Image file not found at path: {}", image.getPath());
                continue;
            }

            try {
                byte[] imageBytes = Files.readAllBytes(file.toPath());
                imageBytesList.add(imageBytes);
            } catch (IOException e) {
                log.error("Failed to read image file: {}", image.getPath(), e);
            }
        }

        if (imageBytesList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(imageBytesList);
    }
}
