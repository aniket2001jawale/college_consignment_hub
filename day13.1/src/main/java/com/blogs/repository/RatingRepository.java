package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.RatingDTO;


public interface RatingRepository extends JpaRepository<RatingDTO, Long> {

}
