package com.restapi.exceptions;

public class ResourceException extends RuntimeException {
    private final ErrorDetail errorDetail;

    public ResourceException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }
}