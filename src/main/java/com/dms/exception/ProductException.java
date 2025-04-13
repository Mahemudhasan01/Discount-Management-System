package com.dms.exception;


public class ProductException extends RuntimeException{
    public ProductException(Long id){
        super("Product not found with Id: " + id);
    }
}
