package com.kishan.courseservice.beans;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CourseResponseBean {
	
	private int courseId;
	private String courseName;
	private String courseDesc;
	
	private List<StudentResponseBean> students;


	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public List<StudentResponseBean> getStudents() {
		return students;
	}

	public void setStudents(List<StudentResponseBean> students) {
		this.students = students;
	}
	
	
	
}
