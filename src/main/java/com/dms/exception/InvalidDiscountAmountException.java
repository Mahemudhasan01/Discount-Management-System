package com.dms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidDiscountAmountException extends RuntimeException {
    public InvalidDiscountAmountException(Double amount) {
        super("Invalid discount amount: " + amount);
    }

    public InvalidDiscountAmountException(String str) {
        super(str);
    }
}
