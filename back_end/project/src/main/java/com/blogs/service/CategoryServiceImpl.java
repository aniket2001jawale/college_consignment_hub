package com.blogs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.CategoryNotFoundException;
import com.blogs.custom_exceptions.StudentNotFoundException;
import com.blogs.dto.CategoryDTO;
import com.blogs.dto.StudentDTO;
import com.blogs.entities.Category;
import com.blogs.entities.Student;
import com.blogs.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService
{
	 @Autowired
	    private CategoryRepository categoryRepository;

	    @Override
	    public void addCategory(Category category) 
	    {
	        categoryRepository.save(category);
	    }

	    @Override
	    public Category getCategoryById(Long id) 
	    {
	    	Optional<Category> category=categoryRepository.findById(id);
	    	if(category.isPresent())
	    	{
	    		return category.get();
	    	}
	    	else
	    	{
	    		throw new CategoryNotFoundException("category is not found!!!");
	    	}
			
	       
	    }

	    @Override
	    public List<Category> getAllCategories() {
	        return categoryRepository.findAll();
	    }

	    @Override
	    public void updateCategory(Long id, Category category) {
	        Category existingCategory = getCategoryById(id);
	        existingCategory.setCategory_name(category.getCategory_name());
	        categoryRepository.save(existingCategory);
	    }

	    @Override
	    public void deleteCategory(Long id) {
	        Category category = getCategoryById(id);
	        categoryRepository.delete(category);
	    }
	

}
