package com.restapi.exceptions;

import com.restapi.dto.ErrorResponse;
import lombok.Getter;

@Getter
public class RMValidateException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public RMValidateException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}