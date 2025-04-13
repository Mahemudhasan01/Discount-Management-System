package com.dms.exception;

public class InvalidDiscountSeasonException extends RuntimeException{
    public InvalidDiscountSeasonException(){
        super("Invalid discount: the season is not within the valid time period.");
    }

    public InvalidDiscountSeasonException(String type){
        super("Invalid discount: the season is not within the valid time period." + type);
    }
}
