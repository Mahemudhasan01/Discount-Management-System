package com.dms.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException() {
        super("Product is out of stock.");
    }

    public OutOfStockException(String str) {
        super(str);
    }
}
