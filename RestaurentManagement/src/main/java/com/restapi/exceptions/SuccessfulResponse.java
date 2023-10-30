package com.restapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulResponse {

    private int code;
    private String status;
    private String message;
    private Object data;
}
