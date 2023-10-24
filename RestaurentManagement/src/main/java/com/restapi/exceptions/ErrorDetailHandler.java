package com.restapi.exceptions;

import com.restapi.models.Menu;
import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.UUID;

public class ErrorDetailHandler {
    public ErrorDetail createNotFoundErrorDetail(UUID id) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Not found");
        errorDetail.setCode(HttpStatus.NOT_FOUND.value());
        errorDetail.setMessage("Check your id '" + id + "' and try again");
        return errorDetail;
    }

    public ErrorDetail createNotFoundErrorDetail(String message, Menu menu) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Bad Request");
        errorDetail.setCode(HttpStatus.BAD_REQUEST.value());
        errorDetail.setMessage(message);
        return errorDetail;
    }
}