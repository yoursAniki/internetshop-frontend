package com.example.internetshop.service;

import com.example.internetshop.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product updatedProduct);

    void deleteProduct(Long id);
}