package com.E_Commerce.services;

import com.E_Commerce.exceptions.ApiException;
import com.E_Commerce.exceptions.ResourceNotFoundException;
import com.E_Commerce.models.Category;
import com.E_Commerce.paylod.CategoryDTO;
import com.E_Commerce.paylod.CategoryResponse;
import com.E_Commerce.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new ApiException("No category available");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category categoryInDB = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(categoryInDB != null){
            throw new ApiException("Category with the name "+ categoryDTO.getCategoryName() +" already exists!");
        }
        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(categoryToSave);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId){return categoryRepository.findById(categoryId);}

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category categoryToBeDeleted = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(categoryToBeDeleted);
        return modelMapper.map(categoryToBeDeleted, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryDTO.setCategoryId(categoryId);

        Category categoryToUpdate = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(categoryToUpdate);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}