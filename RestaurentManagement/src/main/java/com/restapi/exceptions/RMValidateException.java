package com.restapi.exceptions;

import com.restapi.response.ErrorResponse;

public class RMValidateException extends RuntimeException {
    private final ErrorResponse errorDetail;

    public RMValidateException(ErrorResponse errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ErrorResponse getErrorDetail() {
        return errorDetail;
    }
}