package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.request.CreateOrderDto;
import com.ecommerce.order_service.dto.request.SetInvoiceIdDto;
import com.ecommerce.order_service.dto.request.UpdateOrderDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.exception.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder (CreateOrderDto createOrderDto)
            throws ItemNotFoundException, UserNotFoundException, InsufficientAmountException, ExternalServiceUnavailableException;
    OrderResponseDto updateOrder (UpdateOrderDto updateOrderDto)
            throws OrderNotFoundException, ExternalServiceUnavailableException,ItemNotFoundException,InsufficientAmountException;
    void deleteOrder (Integer orderId)
            throws OrderNotFoundException, ExternalServiceUnavailableException,ItemNotFoundException,InsufficientAmountException, UserNotFoundException;
    OrderResponseDto getOrderById (Integer orderId)throws OrderNotFoundException;
    List<OrderResponseDto> getAllOrders();
    void setInvoiceId(SetInvoiceIdDto setInvoiceIdDto) throws OrderNotFoundException;
}
