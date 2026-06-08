package com.ecommerce.product_service.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeItemAmountDto {
    @NotBlank(message = "Item Id is required!")
    private String itemId;

    @NotNull(message = "Quantity is required!")
    private Integer quantity;
}
