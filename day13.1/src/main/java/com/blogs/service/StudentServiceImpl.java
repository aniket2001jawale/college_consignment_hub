package com.blogs.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.StudentNotFoundException;
import com.blogs.dto.StudentDTO;
import com.blogs.entities.Student;
import com.blogs.repository.StudentRepository;

@Service   //spring bean containing business logic

@Transactional    // auto trasactional management

public class StudentServiceImpl implements StudentService
{
	//inject dependency
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void addStudent(StudentDTO studentDTO) 
	{
		studentRepository.save(studentDTO);
	}
	@Override
	public StudentDTO getStudentById(Long id) 
	{
		Optional<StudentDTO> optional = studentRepository.findById(id);
		
		return optional.orElseThrow(() -> new StudentNotFoundException("Invalid Student ID!!!!"));
		
	}

	@Override
	public List<StudentDTO> getAllStudents()
	{
		
		return studentRepository.findAll();
	}

	@Override
	public void updateStudent(StudentDTO studentDTO) 
	{
		if(!studentRepository.findById(studentDTO.getStudent_id()).isPresent())
		{
			throw new StudentNotFoundException("student is not present!!!!");
		}
		
		Student student=new Student();
		student.setStudent_name(studentDTO.getStudent_name());
		student.setUsername(studentDTO.getUsername());
		student.setEmail(studentDTO.getEmail());
		student.setAddress(studentDTO.getAddress());
		studentRepository.save(studentDTO);
	}

	@Override
	public void deleteStudent(Long id) 
	{
		if(studentRepository.findById(id).isPresent())
		{
			studentRepository.deleteById(id);
		}
		else
		{
			throw new StudentNotFoundException("student id is not found!!!!");
		}
		
		
	}
	
	

}
