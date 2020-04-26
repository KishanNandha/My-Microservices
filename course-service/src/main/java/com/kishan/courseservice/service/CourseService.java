package com.kishan.courseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kishan.courseservice.beans.CourseResponseBean;
import com.kishan.courseservice.model.Course;
import com.kishan.courseservice.repository.CourseRepo;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepo courseRepo;
	
	
	public CourseResponseBean getCourseDetails(String courseName) {
		CourseResponseBean responseBean = new CourseResponseBean();
		Course course = courseRepo.findByCourseName(courseName);
		if(null != course) {
			responseBean.setCourseId(course.getCourseId());
			responseBean.setCourseName(course.getCourseName());
			responseBean.setCourseDesc(course.getCourseDesc());
		}
		return responseBean;
	}
	

}
