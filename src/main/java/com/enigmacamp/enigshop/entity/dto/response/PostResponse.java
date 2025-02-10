package com.enigmacamp.enigshop.entity.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PostResponse {
    Integer userId;
    Integer id;
    String title;
    String body;
}
