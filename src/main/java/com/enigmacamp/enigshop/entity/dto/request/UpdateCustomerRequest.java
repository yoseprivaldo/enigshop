package com.enigmacamp.enigshop.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    @NotBlank
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;

    @JsonIgnore
    private MultipartFile image;

    @Override
    public String toString() {
        return "UpdateCustomerRequest{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", image=" + image +
                '}';
    }
}
