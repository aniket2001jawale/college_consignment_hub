package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.CategoryDTO;


public interface CategoryRepository extends JpaRepository<CategoryDTO, Long> 
{

}
