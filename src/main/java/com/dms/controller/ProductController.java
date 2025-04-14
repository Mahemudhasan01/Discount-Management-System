package com.dms.controller;

import com.dms.DTO.DiscountDTO;
import com.dms.DTO.ResponseDTO;
import com.dms.exception.InvalidDiscountAmountException;
import com.dms.exception.ProductException;
import com.dms.model.Product;
import com.dms.repository.ProductRepository;
import com.dms.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/v1")
public class ProductController {

    private final ProductRepository productRepository;
    private final DiscountService discountService;

    public ProductController(ProductRepository pRepository, DiscountService disService){
        this.productRepository = pRepository;
        this.discountService = disService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable Long id){
        Product product = productRepository.findById(id)
                .orElseThrow( () -> new ProductException(id));

        return ResponseEntity.ok(product);
    }

    @PostMapping("/discount")
    public ResponseEntity<ResponseDTO<Double>> applyDiscount(@Valid @RequestBody DiscountDTO request){
        ResponseDTO<Double> response = discountService.calculateProductDiscount(request)
                             .orElseThrow( () -> new InvalidDiscountAmountException(request.getDiscountValue()));

        return ResponseEntity.ok(response);
    }
}
