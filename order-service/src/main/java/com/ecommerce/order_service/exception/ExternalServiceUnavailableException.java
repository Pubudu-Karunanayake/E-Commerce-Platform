package com.ecommerce.order_service.exception;

public class ExternalServiceUnavailableException extends Exception {
    public ExternalServiceUnavailableException(String message) {
        super(message);
    }
}
