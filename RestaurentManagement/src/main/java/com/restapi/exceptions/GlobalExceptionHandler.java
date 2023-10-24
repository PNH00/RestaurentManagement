package com.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ErrorDetail> handleMenuValidationException(ResourceException ex) {
        ErrorDetail errorDetail = ex.getErrorDetail();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}