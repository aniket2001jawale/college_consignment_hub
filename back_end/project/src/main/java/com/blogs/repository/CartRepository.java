package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blogs.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> 
{
	@Query("SELECT c FROM Cart c WHERE c.student.student_id = :studentId")
    Cart findByStudentId(@Param("studentId") Long studentId);
	
	
	
}
