package com.restapi.exceptions;

import com.restapi.response.ErrorResponse;

public class RMValidateException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public RMValidateException(ErrorResponse errorDetail) {
        this.errorResponse = errorDetail;
    }

    public ErrorResponse getErrorDetail() {
        return errorResponse;
    }
}