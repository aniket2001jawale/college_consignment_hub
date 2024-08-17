package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogs.dto.WatchlistDTO;
import com.blogs.entities.Cart;
import com.blogs.entities.Watchlist;


public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
	@Query("SELECT w FROM Watchlist w WHERE w.student.student_id = :studentId")
    Watchlist findByStudentId(@Param("studentId") Long studentId);

	
	
	
}
