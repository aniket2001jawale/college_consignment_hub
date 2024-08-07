package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.StudentDTO;

public interface StudentRepository extends JpaRepository<StudentDTO, Long> {

}
