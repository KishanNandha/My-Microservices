package com.kishan.courseservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kishan.courseservice.beans.CourseResponseBean;
import com.kishan.courseservice.beans.Student;
import com.kishan.courseservice.beans.StudentResponseBean;
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
			
			RestTemplate restTemplate = new RestTemplate();
			Student[] students = restTemplate.getForObject("http://localhost:8001/students/course/{courseId}", Student[].class,course.getCourseId());
			if(null != students) {
				List<StudentResponseBean> studentResponseBean = Arrays.asList(students)
						.stream()
						.map(student -> new StudentResponseBean(student.getStudentId(), student.getStudentName()))
						.collect(Collectors.toList());
				responseBean.setStudents(studentResponseBean);
			}
		}
		return responseBean;
	}
	

}
