package com.blogs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.CartNotFoundException;
import com.blogs.custom_exceptions.WatchlistNotFoundException;
import com.blogs.dto.CartDTO;
import com.blogs.entities.Cart;
import com.blogs.entities.Watchlist;
import com.blogs.repository.CartRepository;
import com.blogs.repository.StudentRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService 
{
	@Autowired
    private CartRepository cartRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addCart(CartDTO cartDTO) {
       
        if (cartDTO.getSeller_id() == null) {
            throw new IllegalArgumentException("Student ID must not be null");
        }
         Cart cart = modelMapper.map(cartDTO, Cart.class);

        cart.setStudent(studentRepository.findById(cartDTO.getSeller_id())
            .orElseThrow(() -> new RuntimeException("Student not found")));
       

        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new CartNotFoundException("Cart with ID " + id + " not found"));

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        if (cart.getStudent() != null) {
            cartDTO.setSeller_id(cart.getStudent().getStudent_id());
        }
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
            .map(cart -> {
                CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                if (cart.getStudent() != null) {
                    cartDTO.setSeller_id(cart.getStudent().getStudent_id());
                }
                return cartDTO;
            })
            .collect(Collectors.toList());
    }

    @Override
    public void updateCart(Long id, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new CartNotFoundException("Cart with ID " + id + " not found"));

        // Update fields
        // Assuming CartDTO contains a list of itemDTOs and other necessary fields
        if (cartDTO.getSeller_id() != null) {
            cart.setStudent(studentRepository.findById(cartDTO.getSeller_id())
                .orElseThrow(() -> new CartNotFoundException("Student with ID " + cartDTO.getSeller_id() + " not found")));
        }

        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
        } else {
            throw new CartNotFoundException("Cart with ID " + id + " not found");
        }
    }

    @Override
    public Long getCartByStudentID(Long studentId) {
        Cart cart = cartRepository.findByStudentId(studentId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for Student ID " + studentId);
        }
        return cart.getCart_id();  // Returning the Cart ID
    }

	
	

}
