package com.blogs.service;

import java.util.List;

import com.blogs.dto.WatchlistDTO;
import com.blogs.entities.Watchlist;

public interface WatchlistService 
{
	 //Add a new watchlist
	  void addWatchlist(WatchlistDTO watchlistDTO);
	    
	 // Get a watchlist by ID
	 WatchlistDTO getWatchlistById(Long id);
	    
	    // Get all watchlists
	    List<WatchlistDTO> getAllWatchlists();
	    
	    // Update an existing watchlist
	    void updateWatchlist(Long id, WatchlistDTO watchlistDTO);
	    
	    // Delete a watchlist by ID
	    void deleteWatchlist(Long id);



		Long getWatchlistByStudentId(Long id);
		
		

}
