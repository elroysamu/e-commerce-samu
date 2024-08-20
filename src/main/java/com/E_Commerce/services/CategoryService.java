package com.E_Commerce.services;

import com.E_Commerce.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    void addCategory(Category categoryName);

    Optional<Category> getCategoryById(Long categoryId);

    String deleteCategory(Long categoryId);

    String updateCategory(int categoryId, Category category);
}
