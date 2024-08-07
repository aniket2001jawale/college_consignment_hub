package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.WatchlistDTO;


public interface WatchlistRepository extends JpaRepository<WatchlistDTO, Long> {

}
