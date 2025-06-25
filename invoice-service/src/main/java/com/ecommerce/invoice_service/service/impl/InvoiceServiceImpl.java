package com.ecommerce.invoice_service.service.impl;

import com.ecommerce.invoice_service.dto.request.SetInvoiceIdDto;
import com.ecommerce.invoice_service.dto.response.InvoiceResponseDto;
import com.ecommerce.invoice_service.dto.response.OrderResponseDto;
import com.ecommerce.invoice_service.dto.response.UserResponseDto;
import com.ecommerce.invoice_service.enums.InvoiceStatus;
import com.ecommerce.invoice_service.exception.ExternalServiceUnavailableException;
import com.ecommerce.invoice_service.exception.InvoiceAlreadyExistingException;
import com.ecommerce.invoice_service.exception.InvoiceNotFoundException;
import com.ecommerce.invoice_service.exception.OrderNotFoundException;
import com.ecommerce.invoice_service.model.Invoice;
import com.ecommerce.invoice_service.repository.InvoiceRepository;
import com.ecommerce.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final RestTemplate restTemplate;


    @Override
    public InvoiceResponseDto generateInvoice(Integer orderId)
            throws ExternalServiceUnavailableException,OrderNotFoundException,InvoiceAlreadyExistingException {
        OrderResponseDto orderResponseDto = fetchOrderById(orderId);
        if (orderResponseDto.getInvoiceStatus() == InvoiceStatus.GENERATED){
            throw new InvoiceAlreadyExistingException("There is an invoice for order id = " + orderId + " already");
        }
        UserResponseDto userResponseDto = fetchUser(orderResponseDto.getUserId());
        float amount = orderResponseDto.getUnitPrice() * orderResponseDto.getAmount();
        float discount = 0.0f;
        if (orderResponseDto.getAmount() >= 10) {
            discount += amount * 0.1f;
        }
        if (userResponseDto.getLoyaltyPoints() >= 5) {
            discount += amount * 0.1f;
        }
        float totalAmount = amount - discount;

        Invoice invoice = new Invoice();
        invoice.setOrderId(orderResponseDto.getOrderId());
        invoice.setCustomerId(orderResponseDto.getUserId());
        invoice.setItemId(orderResponseDto.getProductId());
        invoice.setItemName(orderResponseDto.getProductName());
        invoice.setQuantity(orderResponseDto.getAmount());
        invoice.setUnitPrice(orderResponseDto.getUnitPrice());
        invoice.setAmount(amount);
        invoice.setDiscount(discount);
        invoice.setTotal(totalAmount);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        setInvoiceId(savedInvoice.getOrderId(), savedInvoice.getInvoiceId());
        return new InvoiceResponseDto(
                savedInvoice.getInvoiceId(),
                savedInvoice.getOrderId(),
                savedInvoice.getCustomerId(),
                savedInvoice.getItemId(),
                savedInvoice.getItemName(),
                savedInvoice.getQuantity(),
                savedInvoice.getUnitPrice(),
                savedInvoice.getAmount(),
                savedInvoice.getDiscount(),
                savedInvoice.getTotal()
        );

    }

    @Override
    public void deleteInvoice(Integer invoiceId) throws InvoiceNotFoundException, OrderNotFoundException,ExternalServiceUnavailableException {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() ->
                new InvoiceNotFoundException("There is no invoice with invoice id  = "+ invoiceId));
        setInvoiceId(invoice.getOrderId(), null);
        invoiceRepository.delete(invoice);
    }

    @Override
    public InvoiceResponseDto getInvoiceById(Integer invoiceId) throws InvoiceNotFoundException {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(()->
                new InvoiceNotFoundException("There is bo invoice with invoice id  = " + invoiceId));
        return new InvoiceResponseDto(
                invoice.getInvoiceId(),
                invoice.getOrderId(),
                invoice.getCustomerId(),
                invoice.getItemId(),
                invoice.getItemName(),
                invoice.getQuantity(),
                invoice.getUnitPrice(),
                invoice.getAmount(),
                invoice.getDiscount(),
                invoice.getTotal()
        );
    }

    @Override
    public List<InvoiceResponseDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceResponseDto> invoiceResponseDtoList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceResponseDto invoiceResponseDto = new InvoiceResponseDto(
                    invoice.getInvoiceId(),
                    invoice.getOrderId(),
                    invoice.getCustomerId(),
                    invoice.getItemId(),
                    invoice.getItemName(),
                    invoice.getQuantity(),
                    invoice.getUnitPrice(),
                    invoice.getAmount(),
                    invoice.getDiscount(),
                    invoice.getTotal()
            );
            invoiceResponseDtoList.add(invoiceResponseDto);
        }
        return invoiceResponseDtoList;
    }





    private OrderResponseDto fetchOrderById(Integer orderId) throws OrderNotFoundException,ExternalServiceUnavailableException {
        String orderServiceUrl = "http://localhost:8082/api/orders/" + orderId;
        try {
            return restTemplate.getForObject(orderServiceUrl, OrderResponseDto.class);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new OrderNotFoundException("There is no order with OrderId : " + orderId);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("Order service is currently unavailable");
        }
    }
    private UserResponseDto fetchUser (Integer userId)throws ExternalServiceUnavailableException {
        String userServiceUrl = "http://localhost:8080/api/users/" + userId;
        try{
            return restTemplate.getForObject(userServiceUrl, UserResponseDto.class);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("User service is currently unavailable");
        }
    }
    private void setInvoiceId(Integer orderId, Integer invoiceId) throws OrderNotFoundException,ExternalServiceUnavailableException {
        String orderServiceUrl = "http://localhost:8082/api/orders/set-invoices";
        SetInvoiceIdDto setInvoiceIdDto = new SetInvoiceIdDto();
        setInvoiceIdDto.setOrderId(orderId);
        setInvoiceIdDto.setInvoiceId(invoiceId);
        try{
            restTemplate.postForObject(orderServiceUrl, setInvoiceIdDto, void.class);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new OrderNotFoundException("There is no order with OrderId : " + orderId);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("Order service is currently unavailable");
        }
    }
}
