package com.ecommerce.invoice_service.exception;

public class ExternalServiceUnavailableException extends Exception {
    public ExternalServiceUnavailableException(String message) {
        super(message);
    }
}
