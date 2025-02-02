package com.enigmacamp.enigshop.utils.mapper;

import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.entity.dto.response.PagingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityMapper {
    public static  <T> ResponseEntity<CommonResponse<T>> mapToResponseEntity (
            HttpStatus status,
            String message,
            T data,
            PagingResponse paging

    ){
        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .paging(paging)
                .build();

        return ResponseEntity
                .status(status)
                .header("content-Type", "application/json")
                .body(response);
    }

}
