package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.dto.request.CreateOrderDto;
import com.ecommerce.order_service.dto.request.SetInvoiceIdDto;
import com.ecommerce.order_service.dto.request.UpdateOrderDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.exception.*;
import com.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto)
            throws ItemNotFoundException, UserNotFoundException, InsufficientAmountException, ExternalServiceUnavailableException {
        OrderResponseDto orderResponseDto = orderService.createOrder(createOrderDto);
        URI location = URI.create("api/orders/" + orderResponseDto.getOrderId());
        return ResponseEntity.created(location).body(orderResponseDto);
    }

    @PatchMapping("/orders")
    public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody @Valid UpdateOrderDto updateOrderDto)
        throws ItemNotFoundException, InsufficientAmountException, ExternalServiceUnavailableException, OrderNotFoundException {
        OrderResponseDto orderResponseDto = orderService.updateOrder(updateOrderDto);
        return ResponseEntity.ok().body(orderResponseDto);
    }

    @DeleteMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder (@PathVariable Integer orderId)
            throws OrderNotFoundException, ExternalServiceUnavailableException,ItemNotFoundException,InsufficientAmountException, UserNotFoundException {
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById (@PathVariable Integer id) throws OrderNotFoundException {
        OrderResponseDto orderResponseDto = orderService.getOrderById(id);
        return ResponseEntity.ok().body(orderResponseDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getAllOrders();
        return ResponseEntity.ok().body(orderResponseDtoList);
    }

    @PostMapping("/orders/set-invoices")
    public void setInvoiceId(@RequestBody SetInvoiceIdDto setInvoiceIdDto)
            throws OrderNotFoundException {
        orderService.setInvoiceId(setInvoiceIdDto);
    }
}
