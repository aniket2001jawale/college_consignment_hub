package com.blogs.service;

import java.util.List;

import com.blogs.dto.StudentDTO;

public interface StudentService 
{
	//add student in student table
	void addStudent(StudentDTO studentDTO);
	
	//get student details by id
    StudentDTO getStudentById(Long id);
    
    //get all student details
    List<StudentDTO> getAllStudents();
    
    //update student details
    void updateStudent(StudentDTO studentDTO);
    
    //delete student detail by id
    void deleteStudent(Long id);

}
