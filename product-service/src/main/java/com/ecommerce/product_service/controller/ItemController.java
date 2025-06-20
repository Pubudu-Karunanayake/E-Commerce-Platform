package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.item.CreateItemDto;
import com.ecommerce.product_service.dto.item.ChangeItemAmountDto;
import com.ecommerce.product_service.dto.item.ItemResponseDto;
import com.ecommerce.product_service.dto.item.UpdateItemDto;
import com.ecommerce.product_service.exception.CategoryNotFoundException;
import com.ecommerce.product_service.exception.InsufficientAmountException;
import com.ecommerce.product_service.exception.ItemNotFoundException;
import com.ecommerce.product_service.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/items")
    public ResponseEntity<ItemResponseDto> createNewItem
            (@Valid @RequestBody CreateItemDto createItemDto) throws CategoryNotFoundException {
        ItemResponseDto itemResponseDto = itemService.CreateNewItem(createItemDto);
        URI location = URI.create("/api/items/" + itemResponseDto.getId());
        return ResponseEntity.created(location).body(itemResponseDto);
    }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem (@PathVariable Integer id) throws ItemNotFoundException {
        itemService.deleteItemById(id);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ItemResponseDto> getItem (@PathVariable Integer id) throws ItemNotFoundException {
        ItemResponseDto itemResponseDto = itemService.getItemById(id);
        return ResponseEntity.ok(itemResponseDto);
    }

//    @GetMapping("/items")
//    public ResponseEntity<List<ItemResponseDto>> getAllUsers () {
//        List<ItemResponseDto> itemResponseDtoList = itemService.getAllItems();
//        return ResponseEntity.ok(itemResponseDtoList);
//    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ItemResponseDto> updateItem
            (@PathVariable Integer id, @Valid @RequestBody UpdateItemDto updateItemDto)
            throws ItemNotFoundException, CategoryNotFoundException{
        ItemResponseDto itemResponseDto = itemService.updateItemById(id, updateItemDto);
        return ResponseEntity.ok(itemResponseDto);
    }

    @GetMapping("/categories/{categoryId}/items")
    public ResponseEntity<List<ItemResponseDto>> getItemsByCategory
            (@PathVariable Integer categoryId) throws CategoryNotFoundException {
        List<ItemResponseDto> itemResponseDtoList = itemService.getItemsByCategoryId(categoryId);
        return ResponseEntity.ok(itemResponseDtoList);
    }

    @PostMapping("/items/amount-changes")
    public void changeItemAmount (@RequestBody @Valid ChangeItemAmountDto changeItemAmountDto)
            throws ItemNotFoundException, InsufficientAmountException {
        itemService.changeItemAmount(changeItemAmountDto);
    }

}
