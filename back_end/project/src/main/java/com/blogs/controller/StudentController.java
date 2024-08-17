package com.blogs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogs.custom_exceptions.StudentNotFoundException;
import com.blogs.dto.ApiResponse;
import com.blogs.dto.StudentDTO;
import com.blogs.entities.Student;
import com.blogs.repository.StudentRepository;
import com.blogs.service.StudentService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@GetMapping
	public List<StudentDTO> getAllDetails() {
		return studentService.getAllStudents();
	}

	@PostMapping("/addstudent")
	public ResponseEntity<ApiResponse> addStudent(@RequestBody StudentDTO studentDTO) {
		studentService.addStudent(studentDTO);
		return new ResponseEntity<ApiResponse>(new ApiResponse("student added sucessfully!!!"), HttpStatus.CREATED);
	}
	@PutMapping("/updatestudent/{id}")
	public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
	    if (id == null) {
	        return new ResponseEntity<>(new ApiResponse("ID cannot be null"), HttpStatus.BAD_REQUEST);
	    }

	    if (studentDTO == null || studentDTO.getStudent_id() == null) {
	        return new ResponseEntity<>(new ApiResponse("StudentDTO or ID cannot be null"), HttpStatus.BAD_REQUEST);
	    }

	    if (!studentDTO.getStudent_id().equals(id)) {
	        return new ResponseEntity<>(new ApiResponse("ID mismatch"), HttpStatus.BAD_REQUEST);
	    }

	    try {
	        studentService.updateStudent(studentDTO);
	        return new ResponseEntity<>(new ApiResponse("Student has been updated"), HttpStatus.OK);
	    } catch (StudentNotFoundException e) {
	        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the error
	        return new ResponseEntity<>(new ApiResponse("Failed to update student"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}




	@DeleteMapping("/{student_Id}")
	public ResponseEntity<ApiResponse> deleteStudentDetails(@PathVariable Long student_Id) {
		try {
			studentService.deleteStudent(student_Id);
			return ResponseEntity.ok(new ApiResponse("deleted successfully"));
		} catch (StudentNotFoundException e) {
			return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
		StudentDTO studentDTO = studentService.getStudentById(id);

		if (studentDTO != null && studentDTO.getStudent_id() != null) {
			return ResponseEntity.ok(studentDTO);
		} else {
			throw new StudentNotFoundException("Student with id " + id + " not found");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<StudentDTO> studentLogin(@RequestBody StudentDTO studentDTO) {
		StudentDTO checkStudent = studentService.loginStudent(studentDTO.getEmail(), studentDTO.getPassword());
		if (checkStudent != null)
			return ResponseEntity.ok(checkStudent);
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

	}

}
