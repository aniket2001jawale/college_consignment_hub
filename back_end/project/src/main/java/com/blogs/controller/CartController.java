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
import com.blogs.dto.CartDTO;
import com.blogs.service.CartService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/cart")
public class CartController 
{
	@Autowired
    private CartService cartService;

    @GetMapping
    public List<CartDTO> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping("/addcart")
    public ResponseEntity<ApiResponse> addCart(@RequestBody CartDTO cartDTO) {
        cartService.addCart(cartDTO);
        return new ResponseEntity<>(new ApiResponse("Cart added successfully!"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        CartDTO cartDTO = cartService.getCartById(id);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        cartService.updateCart(id, cartDTO);
        return ResponseEntity.ok(new ApiResponse("Cart updated successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok(new ApiResponse("Cart deleted successfully!"));
    }
    

    @GetMapping("/student/{id}")
    
    public ResponseEntity<Long> getStudentID(@PathVariable Long id) {
        Long cartId = cartService.getCartByStudentID(id);
        return ResponseEntity.ok(cartId);
    }
     
}
