package com.example.productcrud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.productcrud.dto.ProductRequest; // Import your ProductRequest class
import com.example.productcrud.entities.Category;
import com.example.productcrud.entities.Product;
import com.example.productcrud.repositories.CategoryRepository;
import com.example.productcrud.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product createProduct(ProductRequest productRequest) { // Accept ProductRequest instead of individual params
        Product product = new Product();
        product.setName(productRequest.getName()); // Set name from ProductRequest
        product.setPrice(productRequest.getPrice()); // Set price from ProductRequest

        // Find the category using categoryId from ProductRequest
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product productDetails) {
        // Retrieve the existing product
        Product product = getProductById(id);

        // Update the fields of the existing product
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        // Update other fields as necessary

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        // Check if the product exists before deletion
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
