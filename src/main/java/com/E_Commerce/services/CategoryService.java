package com.E_Commerce.services;

import com.E_Commerce.models.Category;
import com.E_Commerce.payload.CategoryDTO;
import com.E_Commerce.payload.CategoryResponse;

import java.util.Optional;

public interface CategoryService {

    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    Optional<Category> getCategoryById(Long categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO category);
}
