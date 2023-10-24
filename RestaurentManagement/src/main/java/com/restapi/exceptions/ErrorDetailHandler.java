package com.restapi.exceptions;

import java.util.UUID;

public abstract class ErrorDetailHandler {
    public abstract ErrorDetail createNotFoundErrorDetail(UUID id);

    public abstract ErrorDetail createNotFoundErrorDetail(String message);
}