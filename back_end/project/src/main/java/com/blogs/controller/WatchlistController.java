package com.blogs.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.blogs.custom_exceptions.WatchlistNotFoundException;
import com.blogs.dto.ApiResponse;
import com.blogs.dto.ItemDTO;
import com.blogs.dto.WatchlistDTO;
import com.blogs.entities.Watchlist;
import com.blogs.repository.StudentRepository;
import com.blogs.service.WatchlistService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/watchlist")
public class WatchlistController 
{
	 @Autowired
	 private WatchlistService watchlistService;
	 


	 
	 @GetMapping
	 public List<WatchlistDTO> getAllWatchlists()
	 {
	       return watchlistService.getAllWatchlists();
	        
	 }
	 
	 
	 @PostMapping("/addwatchlist")
	    public ResponseEntity<ApiResponse> addWatchlist(@RequestBody WatchlistDTO watchlistDTO) {
	        watchlistService.addWatchlist(watchlistDTO);
	        return new ResponseEntity<>(new ApiResponse("Watchlist added successfully!"), HttpStatus.CREATED);
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<WatchlistDTO> getWatchlistById(@PathVariable Long id) 
	 {
	       
	            WatchlistDTO watchlistDTO = watchlistService.getWatchlistById(id);
	            if(watchlistDTO!=null && watchlistDTO.getWatchlist_id()!=null)
	            {
	            	return ResponseEntity.ok(watchlistDTO);
	            }
	            else
	            {
	            	throw new WatchlistNotFoundException("watchlist is not found!!!");
	            }
	            	
	            
	  }
	 
	 @PutMapping("/updatewatchlist/{id}")
	    public ResponseEntity<ApiResponse> updateWatchlist(@PathVariable Long id, @RequestBody WatchlistDTO watchlistDTO) {
	        try {
	            watchlistService.updateWatchlist(id, watchlistDTO);
	            return ResponseEntity.ok(new ApiResponse("Watchlist updated successfully"));
	        } catch (WatchlistNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse> deleteWatchlist(@PathVariable Long id) {
	        try {
	            watchlistService.deleteWatchlist(id);
	            return ResponseEntity.ok(new ApiResponse("Watchlist deleted successfully"));
	        } catch (WatchlistNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
	        }
	    }
	 
	    @GetMapping("/student/{id}")
	    
	    public ResponseEntity<Long> getStudentID(@PathVariable Long id) {
	        Long watchId = watchlistService.getWatchlistByStudentId(id);
	        return ResponseEntity.ok(watchId);
	    }
	   
	    
	    
	 


}
