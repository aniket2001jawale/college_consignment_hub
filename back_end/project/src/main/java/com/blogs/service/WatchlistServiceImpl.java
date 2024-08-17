package com.blogs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.CartNotFoundException;
import com.blogs.custom_exceptions.ItemNotFoundException;
import com.blogs.custom_exceptions.WatchlistNotFoundException;
import com.blogs.dto.ItemDTO;
import com.blogs.dto.RatingDTO;
import com.blogs.dto.WatchlistDTO;
import com.blogs.entities.Cart;
import com.blogs.entities.Item;
import com.blogs.entities.Rating;
import com.blogs.entities.Watchlist;
import com.blogs.repository.ItemRepository;
import com.blogs.repository.StudentRepository;
import com.blogs.repository.WatchlistRepository;

@Service
@Transactional
public class WatchlistServiceImpl implements WatchlistService
{
	@Autowired
	private WatchlistRepository watchlistRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	 
	 
	public void addWatchlist(WatchlistDTO watchlistDTO) 
	{
		 if (watchlistDTO.getBuyer_id() == null) {
	            throw new IllegalArgumentException("Student ID must not be null");
	        }
	        Watchlist watchlist = modelMapper.map(watchlistDTO, Watchlist.class);

	        watchlist.setStudent(studentRepository.findById(watchlistDTO.getBuyer_id())
	            .orElseThrow(() -> new RuntimeException("Student not found")));
	         watchlistRepository.save(watchlist);
    }
	
	public WatchlistDTO getWatchlistById(Long id) 
	{
		Watchlist watchlist=watchlistRepository.findById(id)
				.orElseThrow(()->new WatchlistNotFoundException("watchlist id not found"));
		WatchlistDTO watchlistDTO=modelMapper.map(watchlist, WatchlistDTO.class);
		watchlistDTO.setBuyer_id(watchlist.getStudent().getStudent_id());
		return watchlistDTO;
	}
	public List<WatchlistDTO> getAllWatchlists() 
	{
	        
		List<Watchlist> watchlists = watchlistRepository.findAll();
		 return watchlists.stream()
			        .map(watchlist -> {
			            WatchlistDTO watchlistDTO = modelMapper.map(watchlist, WatchlistDTO.class);
			            if (watchlist.getStudent() != null) {
			                watchlistDTO.setBuyer_id(watchlist.getStudent().getStudent_id());
			            } else {
			                watchlistDTO.setBuyer_id(null); // Handle case where student is null
			            }
			            return watchlistDTO;
			        })
			        .collect(Collectors.toList());
		
	
	 }
	
	public void updateWatchlist(Long id, WatchlistDTO watchlistDTO) 
	{
        Watchlist watchlist = watchlistRepository.findById(id)
            .orElseThrow(() -> new WatchlistNotFoundException("Watchlist ID not found"));

        // Update fields
       
        

        // Save updated watchlist
        watchlistRepository.save(watchlist);
    }
	public void deleteWatchlist(Long watchlistId) {
	    Watchlist watchlist = watchlistRepository.findById(watchlistId)
	            .orElseThrow(() -> new WatchlistNotFoundException("Watchlist not found"));

	    List<Item> items = watchlist.getItem();
	    for (Item item : items) {
	        item.setWatchlist(null);
	    }
	    itemRepository.saveAll(items); // Update the items in the database

	    // Now delete the watchlist
	    watchlistRepository.delete(watchlist);
	    
//	    for (Item item : watchlist.getItem()) {
//	        item.setWatchlist(null);
//	        itemRepository.save(item);
//	    }
//
//	    // Now delete the watchlist
//	    watchlistRepository.delete(watchlist);
	}

	@Override
	public Long getWatchlistByStudentId(Long id) {
		  Watchlist watchList= watchlistRepository.findByStudentId(id);
	        if (watchList == null) {
	            throw new WatchlistNotFoundException("WatchLIst not found for Student ID " + id);
	        }
	        return watchList.getWatchlist_id();
	}

	
}
