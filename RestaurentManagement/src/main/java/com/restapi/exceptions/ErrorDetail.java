package com.restapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String timestamp;
<<<<<<< HEAD
    private int code;
    private String status;
=======
    private String status;
    private int code;
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
    private String message;
}