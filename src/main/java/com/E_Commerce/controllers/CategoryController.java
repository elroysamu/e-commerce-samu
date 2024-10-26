package com.E_Commerce.controllers;

import com.E_Commerce.config.AppConstants;
import com.E_Commerce.models.Category;
import com.E_Commerce.payload.CategoryDTO;
import com.E_Commerce.payload.CategoryResponse;
import com.E_Commerce.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping("api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        return new ResponseEntity<>(categoryServiceImpl.getCategories(pageNumber, pageSize, sortBy, sortOrder),HttpStatus.OK);
    }

    @PostMapping("api/admin/addCategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO categoryDTO1 = categoryServiceImpl.createCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTO1, HttpStatus.OK);
    }

    @GetMapping("api/public/categoryById/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId){
        Optional<Category> categoryOpt = categoryServiceImpl.getCategoryById(categoryId);

        return categoryOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("api/admin/deleteCategoryById/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
        return new ResponseEntity<>(categoryServiceImpl.deleteCategory(categoryId), HttpStatus.OK);
    }

    @PutMapping("api/admin/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryServiceImpl.updateCategory(categoryId,categoryDTO), HttpStatus.OK);
    }
}
