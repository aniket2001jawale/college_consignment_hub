package com.blogs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogs.dto.ApiResponse;
import com.blogs.entities.Category;
import com.blogs.entities.Student;
import com.blogs.service.CategoryService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/category")
public class CategoryController 
{
	 @Autowired
	    private CategoryService categoryService;

	    @PostMapping("/addcategory")
	    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
	        categoryService.addCategory(category);
	        return new ResponseEntity<>(new ApiResponse("Category added successfully!"), HttpStatus.CREATED);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
	        Category category = categoryService.getCategoryById(id);
	        return ResponseEntity.ok(category);
	    }

	    @GetMapping
	    public ResponseEntity<List<Category>> getAllCategories() {
	        List<Category> categories = categoryService.getAllCategories();
	        return ResponseEntity.ok(categories);
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
	        categoryService.updateCategory(id, category);
	        return new ResponseEntity<>(new ApiResponse("Category updated successfully!"), HttpStatus.OK);
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
	        categoryService.deleteCategory(id);
	        return new ResponseEntity<>(new ApiResponse("Category deleted successfully!"), HttpStatus.OK);
	    }
	    

}
