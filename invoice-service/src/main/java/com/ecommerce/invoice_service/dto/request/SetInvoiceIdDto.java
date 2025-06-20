package com.ecommerce.invoice_service.dto.request;

import lombok.Data;

@Data
public class SetInvoiceIdDto {
    private Integer orderId;
    private Integer invoiceId;
}
