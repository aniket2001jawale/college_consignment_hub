package com.blogs.controller;

import java.time.LocalDateTime;
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

import com.blogs.custom_exceptions.ItemNotFoundException;
import com.blogs.dto.ApiResponse;
import com.blogs.dto.ItemDTO;
import com.blogs.entities.Item;
import com.blogs.service.ItemService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/item")
public class ItemController 
{
	 @Autowired
	    private ItemService itemService;

	    // Add a new item
	    @PostMapping
	    public ResponseEntity<ApiResponse> addItem(@RequestBody ItemDTO itemDTO) {
	        itemService.addItem(itemDTO);
	        ApiResponse response = new ApiResponse("Item added successfully");
	        response.setTimeStamp(LocalDateTime.now());
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    // Get an item by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) 
	    {
	        ItemDTO itemDTO = itemService.getItemById(id);
	        if(itemDTO!=null && itemDTO.getItem_id()!=null)
	        {
	        	return ResponseEntity.ok(itemDTO);
	        }
	        else
	        {
	        	throw new ItemNotFoundException("item not found");
	        }
	    }

	    // Get all items
	    @GetMapping
	    public ResponseEntity<List<ItemDTO>> getAllItems() {
	        List<ItemDTO> items = itemService.getAllItems();
	        return ResponseEntity.ok(items);
	    }

	    // Update an existing item
	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
	        itemService.updateItem(id, itemDTO);
	        ApiResponse response = new ApiResponse("Item updated successfully");
	        response.setTimeStamp(LocalDateTime.now());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    // Delete an item by ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long id) {
	        itemService.deleteItem(id);
	        ApiResponse response = new ApiResponse("Item deleted successfully");
	        response.setTimeStamp(LocalDateTime.now());
	        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	    }
	    
	    
	    
	    
	    @GetMapping("/cart/{cartId}")
	    public List<Item> getItemIdsByCartId(@PathVariable Long cartId) {
	        return itemService.getItemsByCartId(cartId);
	    }

	    @GetMapping("/watchlist/{watchlistId}")
	    public List<Item> getItemIdsByWatchlistId(@PathVariable Long watchlistId) {
	        return itemService.getItemsByWatchlistId(watchlistId);
	    }
	  

}
