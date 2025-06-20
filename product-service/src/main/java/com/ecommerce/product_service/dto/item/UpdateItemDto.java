package com.ecommerce.product_service.dto.item;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateItemDto {
    private String name;

    @Positive(message = "Invalid amount!")
    private Integer quantity;

    @Positive(message = "Invalid price!")
    private Float price;

    private Integer categoryId;
}
