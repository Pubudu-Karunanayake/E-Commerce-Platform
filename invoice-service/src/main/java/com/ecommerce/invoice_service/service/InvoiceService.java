package com.ecommerce.invoice_service.service;

import com.ecommerce.invoice_service.dto.response.InvoiceResponseDto;
import com.ecommerce.invoice_service.exception.ExternalServiceUnavailableException;
import com.ecommerce.invoice_service.exception.InvoiceNotFoundException;
import com.ecommerce.invoice_service.exception.OrderNotFoundException;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDto generateInvoice(Integer orderId)
            throws ExternalServiceUnavailableException, OrderNotFoundException;
    void deleteInvoice(Integer invoiceId)
            throws InvoiceNotFoundException, OrderNotFoundException,ExternalServiceUnavailableException;
    InvoiceResponseDto getInvoiceById(Integer invoiceId) throws InvoiceNotFoundException;
    List<InvoiceResponseDto> getAllInvoices();
}
