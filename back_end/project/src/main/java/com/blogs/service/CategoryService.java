package com.blogs.service;

import java.util.List;

import com.blogs.entities.Category;

public interface CategoryService 
{
	// Add a new category
    void addCategory(Category category);

    // Get a category by ID
    Category getCategoryById(Long id);

    // Get all categories
    List<Category> getAllCategories();

    // Update an existing category
    void updateCategory(Long id, Category category);

    // Delete a category by ID
    void deleteCategory(Long id);

}
