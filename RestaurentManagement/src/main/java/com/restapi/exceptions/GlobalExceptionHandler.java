package com.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(RMValidateException.class)
    public ResponseEntity<ErrorDetail> handleMenuValidationException(RMValidateException ex) {
        ErrorDetail errorDetail = ex.getErrorDetail();
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(errorDetail.getCode()));
    }
}