package com.blogs.service;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.StudentNotFoundException;
import com.blogs.dto.StudentDTO;
import com.blogs.entities.Cart;
import com.blogs.entities.Student;
import com.blogs.entities.Watchlist;
import com.blogs.repository.CartRepository;
import com.blogs.repository.StudentRepository;
import com.blogs.repository.WatchlistRepository;

@Service   //spring bean containing business logic

//@Transactional    // auto trasactional management

public class StudentServiceImpl implements StudentService
{
	//inject dependency
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private WatchlistRepository watchlistRepository;
	
	
	@Autowired
	
	
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public void addStudent(StudentDTO studentDTO) 
	{
//		Student student = modelMapper.map(studentDTO, Student.class);
//		//ensure the password before saving
//		String encodedPassword=passwordEncoder.encode(studentDTO.getPassword());
//		student.setPassword(encodedPassword);
		
		//studentRepository.save(student);
		
		
		
		
		
        Student student = modelMapper.map(studentDTO, Student.class);
		//ensure the password before saving
		String encodedPassword=passwordEncoder.encode(studentDTO.getPassword());
		student.setPassword(encodedPassword);
		Cart cart = new Cart(); //gen cart_id
        // Associate cart and watchlist with student
        student.setCart(cart);
        Watchlist watchlist = new Watchlist(); //watch_list
        student.setWatchlist(watchlist);

        // Save cart, watchlist, and student
        cart.setStudent(student);
        watchlist.setStudent(student);

        studentRepository.save(student);  
        cartRepository.save(cart);
        watchlistRepository.save(watchlist);
		
	}
	@Override
	public StudentDTO getStudentById(Long id) {
	    Optional<Student> studentOpt = studentRepository.findById(id);
	    
	    if (studentOpt.isPresent()) 
	    {
	        // Map the found Student to StudentDTO
	        return modelMapper.map(studentOpt.get(), StudentDTO.class);
	    } else 
	    {
	        // Handle the case where the student is not found
	        throw new StudentNotFoundException("Student with id " + id + " not found");
	    }
	}

	

	@Override
	public List<StudentDTO> getAllStudents()
	{
		List<Student> students = studentRepository.findAll();
		return students.stream().map(student->modelMapper.map(student,StudentDTO.class))
				.collect(Collectors.toList());
	}

	
	@Override
	public void updateStudent(StudentDTO studentDTO) {
	    try {
	        Student existingStudent = studentRepository.findById(studentDTO.getStudent_id())
	                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

	        // Update only the fields that are provided
	        if (studentDTO.getStudent_name() != null) {
	            existingStudent.setStudent_name(studentDTO.getStudent_name());
	        }
	        if (studentDTO.getUsername() != null) {
	            existingStudent.setUsername(studentDTO.getUsername());
	        }
	        if (studentDTO.getEmail() != null) {
	            existingStudent.setEmail(studentDTO.getEmail());
	        }
	        if (studentDTO.getAddress() != null) {
	            existingStudent.setAddress(studentDTO.getAddress());
	        }
	        if (studentDTO.getPassword() != null && !studentDTO.getPassword().isEmpty()) {
	            existingStudent.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
	        }

	        studentRepository.save(existingStudent);
	    } catch (Exception e) {
	        // Log the exception
	        e.printStackTrace(); // or use a logging framework
	        throw new RuntimeException("Error updating student", e);
	    }
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
	@Override
	public StudentDTO loginStudent(String email, String password) 
	{
		Student student = studentRepository.findByEmail(email);

    // If student is found and the passwords match
		
    if (student != null && passwordEncoder.matches(password, student.getPassword())) 
    {
        // Convert the entity to a DTO
        return modelMapper.map(student, StudentDTO.class);
    }

    // If the email doesn't exist or the password doesn't match, return null or handle it accordingly
    return null;
		
		
	}
	
	

}
