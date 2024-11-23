package com.E_Commerce.repositories;

import com.E_Commerce.models.Category;
import com.E_Commerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory(Category category, Pageable pageRequest);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageRequest);
}
