package com.ec.E_Commerce.services;

import com.ec.E_Commerce.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    void addCategory(String categoryName);

    Optional<Category> getCategoryById(int categoryId);

    void deleteCategory(int categoryId);
}
