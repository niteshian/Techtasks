package com.example.productcrud.services;

import com.example.productcrud.entities.Category;
import com.example.productcrud.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }


    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingCategory.setName(category.getName());
        // set other fields as necessary
        return categoryRepository.save(existingCategory);
    }


  

    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        categoryRepository.delete(category);
    }}



