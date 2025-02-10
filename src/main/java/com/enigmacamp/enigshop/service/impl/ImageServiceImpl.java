package com.enigmacamp.enigshop.service.impl;

import com.enigmacamp.enigshop.entity.Image;
import com.enigmacamp.enigshop.repository.CustomerRepository;
import com.enigmacamp.enigshop.repository.ImageRepository;
import com.enigmacamp.enigshop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final Path path;
    private final CustomerRepository customerRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, @Value("${app.enigshop.upload.path}") String path, CustomerRepository customerRepository) {
        this.imageRepository = imageRepository;
        this.path = Paths.get(path);
        this.customerRepository = customerRepository;
    }

    @Override
    public Image create(MultipartFile multipartFile, String category) {
        try {
            // todo: valid multipart file
            if (!List.of("image/jpeg", "image/png", "image/gif", "image/jpg").contains(multipartFile.getContentType())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not supported");
            }

            // todo: create folder if not exist
            Path categoryPath = path.resolve(category);
            Files.createDirectories(categoryPath);

            // Todo: Generate MultipartFile to Image and save to disk
            String uniqueFileName = System.currentTimeMillis() + "-" + multipartFile.getOriginalFilename();
            Path filePath = categoryPath.resolve(uniqueFileName);

            // todo: jika file sudah ada maka akan menghasilkan FileAlreadyExistException (StandardOpenOption.CREATE_NEW)
            Files.write(filePath, multipartFile.getBytes(), StandardOpenOption.CREATE_NEW);

            // Todo: Save image to database
            Image image = Image.builder()
                    .name(multipartFile.getOriginalFilename())
                    .path(filePath.toString())
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .build();

            // Todo: Save Image to database
            return imageRepository.saveAndFlush(image);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> getProductImages(String productId) {
        return imageRepository.findByProductId(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found")
        ).getProduct().getImages();
    }

    @Override
    public Image getCustomerImage(String customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        ).getImage();
    }

    @Override
    public void deleteImage(String id) {

    }

}
