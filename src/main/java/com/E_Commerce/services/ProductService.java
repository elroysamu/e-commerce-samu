package com.E_Commerce.services;

import com.E_Commerce.models.Product;
import com.E_Commerce.payload.ProductDTO;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
