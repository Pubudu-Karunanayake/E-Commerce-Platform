package com.ecommerce.order_service.dto.response;

import lombok.Data;

@Data
public class ItemResponseDto {
    private Integer id;
    private String itemName;
    private Integer quantity;
    private Float unitPrice;
    private String categoryName;
}
