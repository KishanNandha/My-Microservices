package com.kishan.courseservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kishan.courseservice.beans.CourseResponseBean;
import com.kishan.courseservice.service.CourseService;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping(path = "/courses/{courseName}")
	public CourseResponseBean getCourseDetails(@PathVariable String courseName) {
		
		return courseService.getCourseDetails(courseName);
	}
	

}
