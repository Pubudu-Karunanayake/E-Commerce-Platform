package com.ecommerce.invoice_service.controller;

import com.ecommerce.invoice_service.dto.response.InvoiceResponseDto;
import com.ecommerce.invoice_service.exception.ExternalServiceUnavailableException;
import com.ecommerce.invoice_service.exception.InvoiceNotFoundException;
import com.ecommerce.invoice_service.exception.OrderNotFoundException;
import com.ecommerce.invoice_service.exception.UserNotFoundException;
import com.ecommerce.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/invoices/{orderId}")
    public ResponseEntity<InvoiceResponseDto> generateInvoice(@PathVariable Integer orderId)
            throws ExternalServiceUnavailableException, OrderNotFoundException, UserNotFoundException {
        InvoiceResponseDto invoiceResponseDto = invoiceService.generateInvoice(orderId);
        URI location = URI.create("api/invoices/" + invoiceResponseDto.getInvoiceId());
        return ResponseEntity.created(location).body(invoiceResponseDto);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice ( @PathVariable Integer invoiceId)
            throws InvoiceNotFoundException, OrderNotFoundException,ExternalServiceUnavailableException{
        invoiceService.deleteInvoice(invoiceId);
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<InvoiceResponseDto> getInvoiceById (@PathVariable Integer invoiceId)
            throws InvoiceNotFoundException {
        InvoiceResponseDto invoiceResponseDto = invoiceService.getInvoiceById(invoiceId);
        return ResponseEntity.ok(invoiceResponseDto);
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceResponseDto>> getAllInvoices() {
        List<InvoiceResponseDto> invoiceResponseDtoList = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoiceResponseDtoList);
    }
}
