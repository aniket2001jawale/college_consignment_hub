package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.CartDTO;


public interface CartRepository extends JpaRepository<CartDTO, Long> 
{

}
