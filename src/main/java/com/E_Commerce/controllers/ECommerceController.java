package com.E_Commerce.controllers;

import com.E_Commerce.models.Category;
import com.E_Commerce.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ECommerceController {

    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping("api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryServiceImpl.getCategories());
    }

    @PostMapping("api/admin/addCategory")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryServiceImpl.addCategory(category);
        return ResponseEntity.ok("added");
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
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryServiceImpl.deleteCategory(categoryId));
    }

    @PutMapping("api/admin/updateCategory/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        return ResponseEntity.ok(categoryServiceImpl.updateCategory(categoryId, category));
    }
}
