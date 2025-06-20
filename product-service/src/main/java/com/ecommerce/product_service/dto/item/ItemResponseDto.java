package com.ecommerce.product_service.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponseDto {
    private Integer id;
    private String itemName;
    private Integer quantity;
    private Float unitPrice;
    private String categoryName;
}
