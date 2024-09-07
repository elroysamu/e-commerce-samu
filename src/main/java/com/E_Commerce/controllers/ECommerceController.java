package com.E_Commerce.controllers;

import com.E_Commerce.models.Category;
import com.E_Commerce.paylod.CategoryDTO;
import com.E_Commerce.paylod.CategoryResponse;
import com.E_Commerce.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class ECommerceController {

    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping("api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        return new ResponseEntity<>(categoryServiceImpl.getCategories(),HttpStatus.OK);
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
