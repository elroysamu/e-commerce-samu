package com.E_Commerce.services;

import com.E_Commerce.models.Category;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryById(Long categoryId);

    void addCategory(Category categoryName);

    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
}
