package com.example.productcrud.repositories;

import com.example.productcrud.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can define custom query methods here if needed
}

