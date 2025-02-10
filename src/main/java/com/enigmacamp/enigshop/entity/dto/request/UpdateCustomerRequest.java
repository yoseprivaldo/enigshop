package com.enigmacamp.enigshop.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    @NotBlank
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Date birthDate;

    @JsonIgnore
    private MultipartFile image;

}
