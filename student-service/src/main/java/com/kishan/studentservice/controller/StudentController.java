package com.kishan.studentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kishan.studentservice.model.Student;
import com.kishan.studentservice.repository.StudentRepo;

@RestController
public class StudentController {

	@Autowired
	private StudentRepo studentRepo;
	
	@GetMapping(path="/students/course/{courseId}")
	List<Student> getStudentsByCourseId(@PathVariable int courseId) {
		return studentRepo.findByCourseId(courseId);
	}
}
