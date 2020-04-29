package com.kishan.studentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kishan.studentservice.model.Student;
import com.kishan.studentservice.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping(path="/students/course/{courseId}")
	List<Student> getStudentsByCourseId(@PathVariable int courseId) {
		return studentService.getStudentsByCourseId(courseId);
	}
}
