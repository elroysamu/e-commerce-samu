package com.ec.E_Commerce.services;

import com.ec.E_Commerce.models.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final List<Category> categories;


    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public void addCategory(String categoryName) {
        Category category;

        if (categories.isEmpty()){
            category = new Category(1L, categoryName);
        }else{
            Long lastId = categories.get(categories.size()-1).getCategoryId();
            category = new Category(lastId+1, categoryName);
        }

        categories.add(category);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        int index = categoryId-1;
        if(index >= 0 && index< categories.size()){
            return Optional.of(categories.get(index));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        categories.removeIf(c -> c.getCategoryId() == categoryId);
    }
}