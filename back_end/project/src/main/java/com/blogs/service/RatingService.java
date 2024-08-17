package com.blogs.service;

import java.util.List;

import com.blogs.dto.RatingDTO;

public interface RatingService 
{
	 // Add a new rating
    void addRating(RatingDTO ratingDTO);

    // Get a rating by ID
    RatingDTO getRatingById(Long id);

    // Get all ratings
    List<RatingDTO> getAllRatings();

    // Update an existing rating
    void updateRating(Long id, RatingDTO ratingDTO);

    // Delete a rating by ID
    void deleteRating(Long id);

}
