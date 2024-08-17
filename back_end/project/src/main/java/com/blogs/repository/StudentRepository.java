package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.StudentDTO;
import com.blogs.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByEmailAndPassword(String email, String password);

	Student findByEmail(String email);

}
