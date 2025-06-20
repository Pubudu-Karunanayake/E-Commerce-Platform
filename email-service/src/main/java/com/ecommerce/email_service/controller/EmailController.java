package com.ecommerce.email_service.controller;

import com.ecommerce.email_service.exceptions.ExternalServiceUnavailableException;
import com.ecommerce.email_service.exceptions.InvoiceNotFoundException;
import com.ecommerce.email_service.service.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailController {
    private final EmailServiceImpl emailService;

    @PostMapping("/emails/{invoiceId}")
    public String sendEmail(@PathVariable Integer invoiceId) throws MessagingException, ExternalServiceUnavailableException, InvoiceNotFoundException {
        emailService.sendInvoiceEmail(invoiceId);
        return "Email sent successfully";
    }

}

