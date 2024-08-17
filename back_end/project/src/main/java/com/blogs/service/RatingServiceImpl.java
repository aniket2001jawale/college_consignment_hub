package com.blogs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.RatingNotFoundException;
import com.blogs.dto.RatingDTO;
import com.blogs.entities.Rating;
import com.blogs.repository.ItemRepository;
import com.blogs.repository.RatingRepository;
import com.blogs.repository.StudentRepository;
@Service
@Transactional
public class RatingServiceImpl implements RatingService
{
	@Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addRating(RatingDTO ratingDTO) {
    	 if (ratingDTO.getBuyer_id() == null) {
    	        throw new IllegalArgumentException("Buyer ID (Student ID) must not be null");
    	    }
    	    if (ratingDTO.getItem_id() == null) {
    	        throw new IllegalArgumentException("Item ID must not be null");
    	    }
        Rating rating = modelMapper.map(ratingDTO, Rating.class);

        rating.setStudent(studentRepository.findById(ratingDTO.getBuyer_id())
            .orElseThrow(() -> new RuntimeException("Student not found")));
        rating.setItem(itemRepository.findById(ratingDTO.getItem_id())
            .orElseThrow(() -> new RuntimeException("Item not found")));

        ratingRepository.save(rating);
    }

    @Override
    public RatingDTO getRatingById(Long id) 
    {
        Rating rating = ratingRepository.findById(id)
            .orElseThrow(() -> new RatingNotFoundException("Rating with ID " + id + " not found"));

        RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
        ratingDTO.setBuyer_id(rating.getStudent().getStudent_id());
        ratingDTO.setItem_id(rating.getItem().getItem_id());

        return ratingDTO;
    }

    @Override
    public List<RatingDTO> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(rating -> {
                    RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
                    ratingDTO.setBuyer_id(rating.getStudent().getStudent_id());
                    ratingDTO.setItem_id(rating.getItem().getItem_id());
                    return ratingDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateRating(Long id, RatingDTO ratingDTO) {
        Rating rating = ratingRepository.findById(id)
            .orElseThrow(() -> new RatingNotFoundException("Rating with ID " + id + " not found"));

        rating.setRating(ratingDTO.getRating());
        rating.setRatingDate(ratingDTO.getRatingDate());
        rating.setComment(ratingDTO.getComment());

        rating.setStudent(studentRepository.findById(ratingDTO.getBuyer_id())
            .orElseThrow(() -> new RuntimeException("Student not found")));
        rating.setItem(itemRepository.findById(ratingDTO.getItem_id())
            .orElseThrow(() -> new RuntimeException("Item not found")));

        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        } else {
            throw new RatingNotFoundException("Rating with ID " + id + " not found");
        }
    }

}
