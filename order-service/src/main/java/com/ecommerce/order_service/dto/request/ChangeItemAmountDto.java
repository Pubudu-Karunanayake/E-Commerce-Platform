package com.ecommerce.order_service.dto.request;

import lombok.Data;

@Data
public class ChangeItemAmountDto {
    private Integer itemId;
    private Integer quantity;
}
