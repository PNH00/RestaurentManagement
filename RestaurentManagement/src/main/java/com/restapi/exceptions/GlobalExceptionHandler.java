package com.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler extends ErrorDetailHandler {

    @ExceptionHandler(RMValidateException.class)
    public ResponseEntity<ErrorDetail> handleMenuValidationException(RMValidateException ex) {
        ErrorDetail errorDetail = ex.getErrorDetail();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ErrorDetail createNotFoundErrorDetail(UUID id) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Not found");
        errorDetail.setCode(HttpStatus.NOT_FOUND.value());
        errorDetail.setMessage("Check your id '" + id + "' and try again");
        return errorDetail;
    }

    @Override
    public ErrorDetail createNotFoundErrorDetail(String message) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Bad Request");
        errorDetail.setCode(HttpStatus.BAD_REQUEST.value());
        errorDetail.setMessage(message);
        return errorDetail;
    }
}