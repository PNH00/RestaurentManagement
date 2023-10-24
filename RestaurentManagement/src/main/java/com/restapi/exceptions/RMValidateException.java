package com.restapi.exceptions;

public class RMValidateException extends RuntimeException {
    private final ErrorDetail errorDetail;

    public RMValidateException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }
}