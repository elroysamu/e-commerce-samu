package com.ec.E_Commerce.controllers;

import com.ec.E_Commerce.models.Category;
import com.ec.E_Commerce.services.CategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
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
    public String createCategory(@RequestBody Category category){
        categoryServiceImpl.addCategory(category.getCategoryName());
        return "added";
    }

    @GetMapping("api/public/categoryById/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId){
        Optional<Category> categoryOpt = categoryServiceImpl.getCategoryById(categoryId);

        return categoryOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("api/admin/deleteCategoryById/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable int categoryId) throws FileNotFoundException {
        return ResponseEntity.ok(categoryServiceImpl.deleteCategory(categoryId));
    }
}
