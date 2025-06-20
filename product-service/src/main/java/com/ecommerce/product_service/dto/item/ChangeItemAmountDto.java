package com.ecommerce.product_service.dto.item;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeItemAmountDto {
    @NotNull(message = "Item Id is required!")
    private Integer itemId;

    @NotNull(message = "Quantity is required!")
    private Integer quantity;
}
