package com.ecommerce.order_service.dto.request;

import com.ecommerce.order_service.enums.InvoiceStatus;
import lombok.Data;

@Data
public class SetInvoiceIdDto {
    private Integer orderId;
    private Integer invoiceId;
    private InvoiceStatus invoiceStatus;
}
