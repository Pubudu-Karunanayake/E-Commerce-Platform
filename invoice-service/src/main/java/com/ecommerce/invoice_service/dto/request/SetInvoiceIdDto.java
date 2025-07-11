package com.ecommerce.invoice_service.dto.request;

import com.ecommerce.invoice_service.enums.InvoiceStatus;
import lombok.Data;

@Data
public class SetInvoiceIdDto {
    private Integer orderId;
    private Integer invoiceId;
    private InvoiceStatus invoiceStatus;
}
