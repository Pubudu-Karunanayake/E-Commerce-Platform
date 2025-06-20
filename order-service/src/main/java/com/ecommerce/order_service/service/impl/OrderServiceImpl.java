package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.request.CreateOrderDto;
import com.ecommerce.order_service.dto.request.ChangeItemAmountDto;
import com.ecommerce.order_service.dto.request.SetInvoiceIdDto;
import com.ecommerce.order_service.dto.request.UpdateOrderDto;
import com.ecommerce.order_service.dto.response.ItemResponseDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.dto.response.UserResponseDto;
import com.ecommerce.order_service.exception.*;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    public OrderResponseDto createOrder(CreateOrderDto createOrderDto)
            throws ItemNotFoundException, UserNotFoundException, InsufficientAmountException, ExternalServiceUnavailableException{
        ItemResponseDto itemResponseDto = fetchItem(createOrderDto.getProductId());
        UserResponseDto userResponseDto = fetchUser(createOrderDto.getUserId());
        changeItemAmount(createOrderDto.getProductId(), -createOrderDto.getQuantity());
        addLoyaltyPoints(createOrderDto.getUserId());
        Order order = new Order();
        order.setProductId(itemResponseDto.getId());
        order.setUserId(userResponseDto.getId());
        order.setAmount(createOrderDto.getQuantity());
        order.setOrderDate(LocalDateTime.now().withNano(0));
        order.setProductName(itemResponseDto.getItemName());
        order.setUnitPrice(itemResponseDto.getUnitPrice());
        Order savedOrder =  orderRepository.save(order);

        return new OrderResponseDto(
                savedOrder.getOrderId(),
                savedOrder.getUserId(),
                savedOrder.getProductId(),
                savedOrder.getProductName(),
                savedOrder.getUnitPrice(),
                savedOrder.getAmount(),
                savedOrder.getOrderDate(),
                savedOrder.getInvoiceId()
        );
    }

    @Override
    public OrderResponseDto updateOrder(UpdateOrderDto updateOrderDto)
            throws OrderNotFoundException,ItemNotFoundException,ExternalServiceUnavailableException,InsufficientAmountException{
        Order order = orderRepository.findById(updateOrderDto.getOrderId()).orElseThrow(()->
                new OrderNotFoundException("There is no order with order Id = " + updateOrderDto.getOrderId()));
        if (order.getProductId() == updateOrderDto.getProductId()) {
            Integer balance = order.getAmount() - updateOrderDto.getQuantity();
            changeItemAmount(updateOrderDto.getProductId(), balance);
            order.setAmount(updateOrderDto.getQuantity());
            Order updatedOrder = orderRepository.save(order);
            return new OrderResponseDto(
                    updateOrderDto.getOrderId(),
                    updatedOrder.getUserId(),
                    updatedOrder.getProductId(),
                    updatedOrder.getProductName(),
                    updatedOrder.getUnitPrice(),
                    updatedOrder.getAmount(),
                    updatedOrder.getOrderDate(),
                    updatedOrder.getInvoiceId()
            );
        }
        changeItemAmount(order.getProductId(), order.getAmount());
        changeItemAmount(updateOrderDto.getProductId(), -updateOrderDto.getQuantity());
        order.setProductId(updateOrderDto.getProductId());
        order.setAmount(updateOrderDto.getQuantity());
        Order updatedOrder = orderRepository.save(order);
        return new OrderResponseDto(
                updateOrderDto.getOrderId(),
                updatedOrder.getUserId(),
                updatedOrder.getProductId(),
                updatedOrder.getProductName(),
                updatedOrder.getUnitPrice(),
                updatedOrder.getAmount(),
                updatedOrder.getOrderDate(),
                updatedOrder.getInvoiceId()
        );
    }

    @Override
    public void deleteOrder(Integer orderId)
            throws OrderNotFoundException,ItemNotFoundException,InsufficientAmountException,ExternalServiceUnavailableException, UserNotFoundException{
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new OrderNotFoundException("There is no order with Order Id = " + orderId));
        changeItemAmount(order.getProductId(),order.getAmount());
        deductLoyaltyPoints(order.getUserId());
        orderRepository.delete(order);
    }

    @Override
    public OrderResponseDto getOrderById(Integer orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new OrderNotFoundException("There is no order with Order Id = " + orderId));
        return new OrderResponseDto(
                order.getOrderId(),
                order.getUserId(),
                order.getProductId(),
                order.getProductName(),
                order.getUnitPrice(),
                order.getAmount(),
                order.getOrderDate(),
                order.getInvoiceId()
        );
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDto orderResponseDto = new OrderResponseDto(
                    order.getOrderId(),
                    order.getUserId(),
                    order.getProductId(),
                    order.getProductName(),
                    order.getUnitPrice(),
                    order.getAmount(),
                    order.getOrderDate(),
                    order.getInvoiceId()
            );
            orderResponses.add(orderResponseDto);
        }
        return orderResponses;
    }

    @Override
    public void setInvoiceId(SetInvoiceIdDto setInvoiceIdDto) throws OrderNotFoundException{
        Order order = orderRepository.findById(setInvoiceIdDto.getOrderId()).orElseThrow(()->
                new OrderNotFoundException("There is no order with order id = " + setInvoiceIdDto.getOrderId()));
        order.setInvoiceId(setInvoiceIdDto.getInvoiceId());
        orderRepository.save(order);
    }



    private ItemResponseDto fetchItem (Integer itemId)throws ItemNotFoundException,ExternalServiceUnavailableException {
        String productServiceUrl = "http://localhost:8081/api/items/" + itemId;
        try {
            return restTemplate.getForObject(productServiceUrl, ItemResponseDto.class);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new ItemNotFoundException("There is no item with ProductId: " + itemId);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("Product service is currently unavailable");
        }
    }
    private UserResponseDto fetchUser (Integer userId)throws UserNotFoundException,ExternalServiceUnavailableException {
        String userServiceUrl = "http://localhost:8080/api/users/" + userId;
        try{
            return restTemplate.getForObject(userServiceUrl, UserResponseDto.class);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("There is no user with UserId: " + userId);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("User service is currently unavailable");
        }
    }
    private void changeItemAmount (Integer itemId, Integer quantity)
            throws ItemNotFoundException,ExternalServiceUnavailableException, InsufficientAmountException{
        String productServiceUrl = "http://localhost:8081/api/items/amount-changes";
        ChangeItemAmountDto changeItemAmountDto = new ChangeItemAmountDto();
        changeItemAmountDto.setItemId(itemId);
        changeItemAmountDto.setQuantity(quantity);
        try{
            restTemplate.postForObject(productServiceUrl, changeItemAmountDto, Void.class);
       }catch (HttpClientErrorException.BadRequest ex) {
            throw new InsufficientAmountException(ex.getResponseBodyAsString());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ItemNotFoundException("There is no item with ProductId: " + itemId);
        } catch (Exception ex) {
            throw new ExternalServiceUnavailableException("Product service is currently unavailable");
        }
    }
    private void addLoyaltyPoints(Integer userId) throws ExternalServiceUnavailableException, UserNotFoundException {
        String userServiceUrl = "http://localhost:8080/api/loyalty-points/additions/" + userId;
        try{
            restTemplate.postForObject(userServiceUrl, null, Void.class);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("There is no user with UserId: " + userId);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("User service is currently unavailable");
        }
    }
    private void deductLoyaltyPoints(Integer userId) throws ExternalServiceUnavailableException, UserNotFoundException {
        String userServiceUrl = "http://localhost:8080/api/loyalty-points/deductions/" + userId;
        try{
            restTemplate.postForObject(userServiceUrl, null, Void.class);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("There is no user with UserId: " + userId);
        }catch (Exception ex) {
            throw new ExternalServiceUnavailableException("User service is currently unavailable");
        }
    }
}
