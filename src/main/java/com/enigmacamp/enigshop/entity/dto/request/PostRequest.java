package com.enigmacamp.enigshop.entity.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostRequest {
    String userId;
    String title;
    String body;
}
