package com.restapi.exceptions;

import lombok.Data;

@Data
public class ErrorDetail {
    private String timestamp;
    private String status;
    private int code;
    private String message;
    private Object data;


    public ErrorDetail(String timestamp, String status, int code, String message, Object data) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ErrorDetail() {
    }
}