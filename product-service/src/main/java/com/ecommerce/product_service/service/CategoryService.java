package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.category.CategoryResponseDto;
import com.ecommerce.product_service.dto.category.CreateCategoryDto;
import com.ecommerce.product_service.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CreateCategoryDto createCategoryDto);
    void deleteCategory(String id) throws CategoryNotFoundException;
    List<CategoryResponseDto> getAllCategories();
}
