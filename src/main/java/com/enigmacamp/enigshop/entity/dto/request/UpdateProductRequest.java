package com.enigmacamp.enigshop.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotBlank
    private String id;
    private String name;
    private Long price;
    private String description;
    private Integer stock;

    @JsonIgnore
    private List<MultipartFile> images;

    @Override
    public String toString() {
        return "UpdateProductRequest{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", price=" + price + ", description='" + description + '\'' + ", stock=" + stock + ", images=" + images + '}';
    }
}
