package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.item.CreateItemDto;
import com.ecommerce.product_service.dto.item.ChangeItemAmountDto;
import com.ecommerce.product_service.dto.item.ItemResponseDto;
import com.ecommerce.product_service.dto.item.UpdateItemDto;
import com.ecommerce.product_service.exception.CategoryNotFoundException;
import com.ecommerce.product_service.exception.InsufficientAmountException;
import com.ecommerce.product_service.exception.ItemNotFoundException;
import com.ecommerce.product_service.model.Category;
import com.ecommerce.product_service.model.Item;
import com.ecommerce.product_service.repository.CategoryRepository;
import com.ecommerce.product_service.repository.ItemRepository;
import com.ecommerce.product_service.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ItemResponseDto CreateNewItem(CreateItemDto createItemDto) throws CategoryNotFoundException{
        Category category = categoryRepository.findById(createItemDto.getCategoryId()).orElseThrow(()->
                new CategoryNotFoundException("There is no category for id = " + createItemDto.getCategoryId()));
        Item item = new Item();
        item.setItemName(createItemDto.getName());
        item.setQuantity(createItemDto.getQuantity());
        item.setUnitPrice(createItemDto.getPrice());
        item.setCategory(category);
        Item savedItem = itemRepository.save(item);
        return new ItemResponseDto(
                savedItem.getId(),
                savedItem.getItemName(),
                savedItem.getQuantity(),
                savedItem.getUnitPrice(),
                savedItem.getCategory().getName()
        );
    }

    @Override
    public void deleteItemById(Integer id) throws ItemNotFoundException {
        Item item = itemRepository.findById(id).orElseThrow(()->
                new ItemNotFoundException("There is no item with id = " + id));
        itemRepository.delete(item);
    }

    @Override
    public ItemResponseDto getItemById(Integer id) throws ItemNotFoundException {
        Item item = itemRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("There is no item with id = " + id));
        return new ItemResponseDto(
                item.getId(),
                item.getItemName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getCategory().getName()
        );
    }

//    @Override
//    public List<ItemResponseDto> getAllItems() {
//            List<Item> items = itemRepository.findAll();
//            List<ItemResponseDto> itemResponse = new ArrayList<>();
//
//            for (Item item : items) {
//                ItemResponseDto itemResponseDto = new ItemResponseDto(
//                        item.getId(),
//                        item.getItemName(),
//                        item.getQuantity(),
//                        item.getUnitPrice(),
//                        item.getCategory().getName()
//                );
//                itemResponse.add(itemResponseDto);
//            }
//
//            return itemResponse;
//    }

    @Override
    public ItemResponseDto updateItemById(Integer id, UpdateItemDto updateItemDto)
            throws ItemNotFoundException , CategoryNotFoundException{
        Item item = itemRepository.findById(id).orElseThrow(()->
                new ItemNotFoundException("There is no item with id = " + id));

        if (updateItemDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateItemDto.getCategoryId()).orElseThrow(()->
                    new CategoryNotFoundException("There is no category with id = " + id));
            item.setCategory(category);
        }
        if (updateItemDto.getName() != null) {
            item.setItemName(updateItemDto.getName());
        }
        if (updateItemDto.getQuantity() != null) {
            item.setQuantity(updateItemDto.getQuantity());
        }
        if (updateItemDto.getPrice() != null) {
            item.setUnitPrice(updateItemDto.getPrice());
        }

        Item savedItem = itemRepository.save(item);
        return new ItemResponseDto(
                savedItem.getId(),
                savedItem.getItemName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getCategory().getName()
        );
    }

    @Override
    public List<ItemResponseDto> getItemsByCategoryId(Integer categoryId) throws CategoryNotFoundException{
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new CategoryNotFoundException("There is no category with id = " + categoryId));
        List<Item> items = category.getItems();
        List<ItemResponseDto> itemResponse = new ArrayList<>();
        for (Item item : items) {
            ItemResponseDto itemResponseDto = new ItemResponseDto(
                    item.getId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getCategory().getName()
            );
            itemResponse.add(itemResponseDto);
        }
        return itemResponse;
    }

    @Override
    @Transactional(rollbackFor = {ItemNotFoundException.class, InsufficientAmountException.class})
    public void changeItemAmount (ChangeItemAmountDto changeItemAmountDto) throws ItemNotFoundException, InsufficientAmountException {
        Item item = itemRepository.findById(changeItemAmountDto.getItemId()).orElseThrow(()->
                new ItemNotFoundException("There is no item with id = " + changeItemAmountDto.getItemId()));
        Integer balance = item.getQuantity() + changeItemAmountDto.getQuantity();
        if (balance < 0) {
            throw new InsufficientAmountException("Only " + item.getQuantity() + " " + item.getItemName() + " are available");
        }
        item.setQuantity(balance);
        itemRepository.save(item);
    }
}
