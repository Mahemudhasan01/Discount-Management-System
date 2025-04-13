package com.dms.DTO;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class DiscountDTO {
    @NotBlank
    private Long productId;

    @NotBlank
    private String discountType; // "flat" or "percentage"

    @PositiveOrZero
    private double discountValue;

    private boolean seasonalDiscountActive;

    @PositiveOrZero
    private double productPrice;

    @Min(1)
    private int quantity;

    private String message;
    private int status;

    public @NotBlank Long getProductId() {
        return productId;
    }

    public void setProductId(@NotBlank Long productId) {
        this.productId = productId;
    }

    public @NotBlank String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(@NotBlank String discountType) {
        this.discountType = discountType;
    }

    @PositiveOrZero
    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(@PositiveOrZero double discountValue) {
        this.discountValue = discountValue;
    }

    public boolean isSeasonalDiscountActive() {
        return seasonalDiscountActive;
    }

    public void setSeasonalDiscountActive(boolean seasonalDiscountActive) {
        this.seasonalDiscountActive = seasonalDiscountActive;
    }

    @PositiveOrZero
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(@PositiveOrZero double productPrice) {
        this.productPrice = productPrice;
    }

    @Min(1)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(1) int quantity) {
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCurrentDateInSeason(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        return (today.isEqual(start) || today.isAfter(start)) &&
                (today.isEqual(end) || today.isBefore(end));
    }

}
