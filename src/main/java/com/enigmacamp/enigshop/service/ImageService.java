package com.enigmacamp.enigshop.service;

import com.enigmacamp.enigshop.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image create(MultipartFile multipartFile, String category);
    void deleteImage(String id);

    List<Image> getAllImages();

    List<Image> getProductImages(String productId);

    Image getCustomerImage(String customerId);
}
