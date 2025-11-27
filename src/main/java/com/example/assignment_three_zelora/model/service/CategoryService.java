package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Category;
import com.example.assignment_three_zelora.model.repos.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}