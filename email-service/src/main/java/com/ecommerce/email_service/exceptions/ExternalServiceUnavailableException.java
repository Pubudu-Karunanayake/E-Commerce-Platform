package com.ecommerce.email_service.exceptions;

public class ExternalServiceUnavailableException extends Exception {
    public ExternalServiceUnavailableException(String message) {
        super(message);
    }
}
