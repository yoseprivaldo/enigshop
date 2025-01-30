package com.enigmacamp.enigshop.controller.exception;

import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.utils.exception.BadRequestException;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({ResourcesNotFoundException.class})
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourcesNotFoundException e){
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CommonResponse<String>> handleBadRequestException(BadRequestException e){
        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String message = String.format("Parameter '%s' must be '%s'. Received: '%s'",
                e.getName(),
                e.getRequiredType().getSimpleName(),
                e.getValue());

        CommonResponse<String> response = CommonResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}



