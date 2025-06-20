package com.ecommerce.order_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateOrderDto {
    @NotNull(message = "User Id is required!")
    private Integer userId;

    @NotNull(message = "Product Id is required!")
    private Integer productId;

    @NotNull(message = "Quantity is required!")
    @Positive(message = "Order quantity should be more than 0 !")
    private Integer quantity;
}
