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

import com.blogs.dto.ApiResponse;
import com.blogs.dto.RatingDTO;
import com.blogs.service.RatingService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/rating")
public class RatingController 
{
	@Autowired
    private RatingService ratingService;

    // Add a new rating
    @PostMapping
    public ResponseEntity<ApiResponse> addRating(@RequestBody RatingDTO ratingDTO) {
        ratingService.addRating(ratingDTO);
        ApiResponse response = new ApiResponse();
        response.setMessage("Rating added successfully");
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get a rating by ID
    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable Long id) {
        RatingDTO ratingDTO = ratingService.getRatingById(id);
        return ResponseEntity.ok(ratingDTO);
    }

    // Get all ratings
    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<RatingDTO> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    // Update an existing rating
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        ratingService.updateRating(id, ratingDTO);
        ApiResponse response = new ApiResponse();
        response.setMessage("Rating updated successfully");
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a rating by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Rating deleted successfully");
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
