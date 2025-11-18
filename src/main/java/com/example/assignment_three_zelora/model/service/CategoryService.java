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

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category updateCategory(Integer id, Category updatedCategory) {
        if (!categoryRepository.existsById(id)) {
            return null;
        }
        updatedCategory.setCategoryId(id);
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}