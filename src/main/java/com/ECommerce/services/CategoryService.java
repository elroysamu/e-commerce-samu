package com.ECommerce.services;

import com.ECommerce.models.Category;
import com.ECommerce.payload.CategoryDTO;
import com.ECommerce.payload.CategoryResponse;

import java.util.Optional;

public interface CategoryService {

    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    Optional<Category> getCategoryById(Long categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO) throws InterruptedException;

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO category);
}
