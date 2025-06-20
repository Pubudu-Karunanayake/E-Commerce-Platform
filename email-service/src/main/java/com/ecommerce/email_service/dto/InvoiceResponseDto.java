package com.ecommerce.email_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceResponseDto {
    private Integer invoiceId;
    private Integer orderId;
    private Integer customerId;
    private Integer itemId;
    private String itemName;
    private Integer quantity;
    private Float unitPrice;
    private Float amount;
    private Float discount;
    private Float total;
}
