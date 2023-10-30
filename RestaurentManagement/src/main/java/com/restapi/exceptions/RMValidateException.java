package com.restapi.exceptions;

import com.restapi.response.ErrorResponse;
import lombok.Getter;

@Getter
public class RMValidateException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public RMValidateException(ErrorResponse errorDetail) {
        this.errorResponse = errorDetail;
    }

}