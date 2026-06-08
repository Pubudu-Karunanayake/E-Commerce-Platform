package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.item.CreateItemDto;
import com.ecommerce.product_service.dto.item.ChangeItemAmountDto;
import com.ecommerce.product_service.dto.item.ItemResponseDto;
import com.ecommerce.product_service.dto.item.UpdateItemDto;
import com.ecommerce.product_service.exception.CategoryNotFoundException;
import com.ecommerce.product_service.exception.InsufficientAmountException;
import com.ecommerce.product_service.exception.ItemNotFoundException;

import java.util.List;

public interface ItemService {
    ItemResponseDto CreateNewItem(CreateItemDto createItemDto) throws CategoryNotFoundException;
    void deleteItemById(String id) throws ItemNotFoundException;
    ItemResponseDto getItemById(String id) throws ItemNotFoundException;
    //List<ItemResponseDto> getAllItems();
    ItemResponseDto updateItemById(String id, UpdateItemDto updateItemDto)
            throws ItemNotFoundException, CategoryNotFoundException;
    List<ItemResponseDto> getItemsByCategoryId(String categoryId) throws CategoryNotFoundException;
    void changeItemAmount(ChangeItemAmountDto changeItemAmountDto) throws ItemNotFoundException, InsufficientAmountException;
}
