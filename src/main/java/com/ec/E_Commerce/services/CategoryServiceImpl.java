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
        int start = 0;
        int end = categories.size()-1;
        while(start <= end){
            int mid = (start+end)/2;
            if(categories.get(mid).getCategoryId() == categoryId){
                return Optional.of(categories.get(mid));
            }else if (categories.get(mid).getCategoryId() > categoryId){
                end = mid-1;
            }else{
                start = mid+1;
            }
        }
        return Optional.empty();
    }

    @Override
    public String deleteCategory(int categoryId) {
        return (categories.removeIf(c -> c.getCategoryId() == categoryId))
                ? "successfully deleted category with id: "+ categoryId
                : "category with id: " + categoryId + " not found";
    }
}