package com.enigmacamp.enigshop.controller.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.enigmacamp.enigshop.entity.dto.response.CommonResponse;
import com.enigmacamp.enigshop.utils.exception.BadCredentialException;
import com.enigmacamp.enigshop.utils.exception.BadRequestException;
import com.enigmacamp.enigshop.utils.exception.ResourcesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.enigmacamp.enigshop.utils.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({ResourcesNotFoundException.class})
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourcesNotFoundException e){
       return mapToResponseEntity(
               HttpStatus.NOT_FOUND,
               e.getMessage(),
               null,
               null
       );
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CommonResponse<String>> handleBadRequestException(BadRequestException e){
        return mapToResponseEntity(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null,
                null
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String message = String.format("Parameter '%s' must be '%s'. Received: '%s'",
                e.getName(),
                e.getRequiredType().getSimpleName(),
                e.getValue());

        return mapToResponseEntity(
                HttpStatus.BAD_REQUEST,
                message,
                null,
                null
        );

    }

    @ExceptionHandler(org.springframework.web.bind.MissingRequestHeaderException.class)
    public ResponseEntity<CommonResponse<String>> handleMissingHeader(Exception e) {
        return mapToResponseEntity(
                HttpStatus.UNAUTHORIZED,
                "Missing Authorization Header",
                null,
                null
        );
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<CommonResponse<String>> handleJwtVerificationException(Exception e){
        return mapToResponseEntity(
                HttpStatus.UNAUTHORIZED,
                "Invalid or Expired token",
                null,
                null
        );
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<CommonResponse<String>> handleBadExceptionCredential(Exception e){
        return mapToResponseEntity(
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                null,
                null
        );
    }

}



