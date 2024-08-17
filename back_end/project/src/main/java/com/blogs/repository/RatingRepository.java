package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.RatingDTO;
import com.blogs.entities.Rating;


public interface RatingRepository extends JpaRepository<Rating, Long> {

}
