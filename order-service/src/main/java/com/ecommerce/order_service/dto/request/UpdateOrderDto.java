package com.ecommerce.order_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateOrderDto {
    @NotNull(message = "Order Id is required!")
    Integer orderId;

    @NotNull(message = "Product Id is required!")
    Integer productId;

    @NotNull(message = "Quantity is required!")
    @Positive(message = "Order quantity should be more than 0 !")
    Integer quantity;
}
