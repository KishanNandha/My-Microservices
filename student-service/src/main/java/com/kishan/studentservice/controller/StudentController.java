package com.kishan.studentservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kishan.studentservice.events.SimpleSourceBean;
import com.kishan.studentservice.model.Student;
import com.kishan.studentservice.repository.StudentRepo;
import com.kishan.studentservice.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private SimpleSourceBean source;
	
	@GetMapping(path="/students/course/{courseId}")
	List<Student> getStudentsByCourseId(@PathVariable int courseId) {
		return studentService.getStudentsByCourseId(courseId);
	}
	
	@PostMapping(path="/students")
	private String saveOrUpdateStudent(@Valid @RequestBody Student student) {
		String returnMsg = "Some error while saving student information";
		Student updatedStudent = studentRepo.save(student);
		if(null != updatedStudent) {
			//Putting event in queue
			source.publishStudentChange("UPDATE", updatedStudent.getStudentId(), updatedStudent.getStudentName());
			returnMsg = "Student data updated";
		}
		return returnMsg;
	}
}
