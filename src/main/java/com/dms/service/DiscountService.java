package com.dms.service;

import com.dms.DTO.DiscountDTO;
import com.dms.DTO.ResponseDTO;

import java.util.Optional;


public interface DiscountService {
    Optional<ResponseDTO<Double>> calculateProductDiscount(DiscountDTO dto);
}
