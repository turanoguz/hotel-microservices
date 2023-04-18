package com.microservices.user.service.exceptions;

import com.microservices.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception){

        String message = exception.getMessage();
        ApiResponse response = ApiResponse.builder()
                .message(message)
                .succes(true)
                .status(HttpStatus.NOT_FOUND)
                .build();

        return  new ResponseEntity<>(response,HttpStatus.NOT_FOUND);


    }
}
