package com.ecommerce.email_service.service;

import com.ecommerce.email_service.dto.InvoiceResponseDto;
import com.ecommerce.email_service.dto.UserResponseDto;
import com.ecommerce.email_service.exceptions.ExternalServiceUnavailableException;
import com.ecommerce.email_service.exceptions.InvoiceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate;

    public void sendInvoiceEmail(Integer invoiceId) throws MessagingException, ExternalServiceUnavailableException,InvoiceNotFoundException {

        InvoiceResponseDto invoiceResponseDto = fetchInvoice(invoiceId);
        UserResponseDto userResponseDto = fetchUser(invoiceResponseDto.getCustomerId());
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(userResponseDto.getEmail());
        helper.setSubject("Invoice Details - Invoice ID: " + invoiceResponseDto.getInvoiceId());
        helper.setText(buildHtmlContent(invoiceResponseDto), true);

        mailSender.send(message);
    }

    private String buildHtmlContent(InvoiceResponseDto invoiceResponseDto) {
        return """
                    <html>
                    <body>
                        <h2>Invoice Details</h2>
                        <table border='1' cellpadding='10' cellspacing='0' style='border-collapse: collapse;'>
                            <tr><th>Invoice ID</th><td>%d</td></tr>
                            <tr><th>Order ID</th><td>%d</td></tr>
                            <tr><th>Customer ID</th><td>%d</td></tr>
                            <tr><th>Item ID</th><td>%d</td></tr>
                            <tr><th>Item Name</th><td>%s</td></tr>
                            <tr><th>Quantity</th><td>%d</td></tr>
                            <tr><th>Unit Price</th><td>%.2f</td></tr>
                            <tr><th>Amount</th><td>%.2f</td></tr>
                            <tr><th>Discount</th><td>%.2f</td></tr>
                            <tr><th>Total</th><td>%.2f</td></tr>
                        </table>
                    </body>
                    </html>
                """.formatted(
                invoiceResponseDto.getInvoiceId(),
                invoiceResponseDto.getOrderId(),
                invoiceResponseDto.getCustomerId(),
                invoiceResponseDto.getItemId(),
                invoiceResponseDto.getItemName(),
                invoiceResponseDto.getQuantity(),
                invoiceResponseDto.getUnitPrice(),
                invoiceResponseDto.getAmount(),
                invoiceResponseDto.getDiscount(),
                invoiceResponseDto.getTotal()
        );
    }

    private UserResponseDto fetchUser(Integer userId) throws ExternalServiceUnavailableException {
        String userServiceUrl = "http://localhost:8080/api/users/" + userId;
        try {
            return restTemplate.getForObject(userServiceUrl, UserResponseDto.class);
        } catch (Exception ex) {
            throw new ExternalServiceUnavailableException("User service is currently unavailable");
        }

    }

    private InvoiceResponseDto fetchInvoice (Integer invoiceId) throws ExternalServiceUnavailableException, InvoiceNotFoundException {
        String invoiceServiceUrl = "http://localhost:8083/api/invoices/" + invoiceId;
        try {
            return restTemplate.getForObject(invoiceServiceUrl, InvoiceResponseDto.class);
        }catch (HttpClientErrorException.NotFound ex) {
           throw new InvoiceNotFoundException("There is no invoice with InvoiceId: " + invoiceId);
        } catch (Exception ex) {
            throw new ExternalServiceUnavailableException("Invoice service currently unavailable");
        }
    }
}
