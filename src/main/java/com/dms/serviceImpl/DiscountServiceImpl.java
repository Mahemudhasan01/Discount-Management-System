package com.dms.serviceImpl;

import com.dms.DTO.DiscountDTO;
import com.dms.DTO.ResponseDTO;
import com.dms.exception.*;
import com.dms.model.Product;
import com.dms.model.Season;
import com.dms.repository.ProductRepository;
import com.dms.repository.SeasonRepository;
import com.dms.service.DiscountService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final ProductRepository productRepository;
    private final SeasonRepository seasonRepository;

    public DiscountServiceImpl (ProductRepository pRepository, SeasonRepository sRePository){
        this.productRepository = pRepository;
        this.seasonRepository = sRePository;
    }

    /*
     * Calculate discount base on discount type
     * DiscountDTO
     * */
    @Override
    public Optional<ResponseDTO<Double>> calculateProductDiscount(DiscountDTO dto) {
        try {
            String discountType = dto.getDiscountType();

            if (discountType.equals("flat")) {//Calculate flat discount here
                return calculateFlatDiscount(dto);
            } else if (discountType.equals("percentage")) {//Calculate percentage discount
                return calculatePercentageDiscount(dto);
            } else {
                throw new InvalidDiscountTypeException(dto.getDiscountType());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Calculate discount by percentage
     * DiscountDTO
     * */
    private Optional<ResponseDTO<Double>> calculatePercentageDiscount(DiscountDTO dto) {
        try {
            Product product = getProductById(dto.getProductId());
            ResponseDTO<Double> responseDTO = new ResponseDTO<>();

            double discountAmount = (product.getPrice() * dto.getDiscountValue()) / 100;
            if(product.getPrice() > discountAmount){//product price should more than product price.
                double discountedPrice = product.getPrice() - discountAmount;
                discountedPrice = calculateSeasonalDiscountIfActive(dto, discountedPrice);

                responseDTO.setData(discountedPrice);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(200);

                return Optional.of(responseDTO);
            }else{
                throw new InvalidDiscountAmountException("Discount exceeded price. Final price set to 0.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Calculate flat discount
     * DiscountDTO
     * */
    private Optional<ResponseDTO<Double>> calculateFlatDiscount(DiscountDTO dto) {
        try {
            Product product = getProductById(dto.getProductId());
            ResponseDTO<Double> responseDTO = new ResponseDTO<>();

            if(dto.getQuantity() > product.getQuantity()){//Validate stock
                throw new OutOfStockException("Order quantity is greater-than available quantity is: " + product.getQuantity());
            }

            if(product.getPrice() > dto.getDiscountValue()){//Product price should be greater than discount value.
                double discountedPrice = product.getPrice() - dto.getDiscountValue();
                discountedPrice = calculateSeasonalDiscountIfActive(dto, discountedPrice);

                responseDTO.setData(discountedPrice);
                responseDTO.setStatus(200);
                responseDTO.setMessage("Success");

                return Optional.of(responseDTO);
            }else{
                throw new InvalidDiscountAmountException("Discount exceeded price. Final price set to 0.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Double calculateSeasonalDiscountIfActive(DiscountDTO dto, Double discountedPrice) {

        if(dto.isSeasonalDiscountActive()){//Check is seasonal discount active or no
            Optional<Season> season = seasonRepository.findByName("Summer Sale");

            if(dto.isCurrentDateInSeason(season.get().getStartDate(), season.get().getEndDate())) {//Is season withing seasonal time period?
                discountedPrice -= (discountedPrice * season.get().getDiscountValue());
                return discountedPrice;
            }else {
                throw new InvalidDiscountSeasonException();
            }
        }else{
            return discountedPrice;//if season discount is not active return same value...
        }
    }

    /*
     * Get Product details by productId
     * Long productId
     * */
    private Product getProductById(@NotBlank Long productId) {
        return productRepository.findById(productId)
                .orElseThrow( () -> new ProductException(productId));
    }
}
