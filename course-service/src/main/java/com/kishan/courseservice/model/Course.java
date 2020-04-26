package com.kishan.courseservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses")
public class Course {
	
	@Column(name="course_id")
	@Id
	private int courseId;
	@Column(name="course_name")
	private String courseName;
	@Column(name="course_desc")
	private String courseDesc;
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

	
}
