package com.ecommerce.invoice_service.exception;

public class InvoiceAlreadyExistingException extends Exception{
    public InvoiceAlreadyExistingException(String message) {
        super(message);
    }
}
