package com.dms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidDiscountTypeException extends RuntimeException {

    public InvalidDiscountTypeException(String type) {
        super("Invalid discount type: " + type);
    }
}
