package com.blogs.service;

import java.util.List;

import com.blogs.dto.CartDTO;

public interface CartService 
{
	// Add a new cart
    void addCart(CartDTO cartDTO);

    // Get a cart by ID
    CartDTO getCartById(Long id);

    // Get all carts
    List<CartDTO> getAllCarts();

    // Update an existing cart
    void updateCart(Long id, CartDTO cartDTO);

    // Delete a cart by ID
    void deleteCart(Long id);
    
    Long getCartByStudentID(Long id);
    
    
}
