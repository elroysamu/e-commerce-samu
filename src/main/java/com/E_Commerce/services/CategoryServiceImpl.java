package com.E_Commerce.services;

import com.E_Commerce.exceptions.ApiException;
import com.E_Commerce.exceptions.ResourceNotFoundException;
import com.E_Commerce.models.Category;
import com.E_Commerce.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    CategoryRepository categoryRepository;


    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new ApiException("No category available");
        }
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new ApiException("Category with the name "+ category.getCategoryName() +" already exists!");
        }
        categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId){return categoryRepository.findById(categoryId);}

    @Override
    public String deleteCategory(Long categoryId) {
        Category categoryToBeDeleted = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(categoryToBeDeleted);
        return "category with id: "+ categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}