package com.ecommerce.email_service.exceptions;

import com.ecommerce.email_service.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdviser {
    @ExceptionHandler(ExternalServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleExternalServiceUnavailable(ExternalServiceUnavailableException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
