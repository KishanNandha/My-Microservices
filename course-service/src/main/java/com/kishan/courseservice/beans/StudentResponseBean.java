package com.kishan.courseservice.beans;

public class StudentResponseBean {
	
	private int student_id;
	private String student_name;
	
	
	public StudentResponseBean(int student_id, String student_name) {
		super();
		this.student_id = student_id;
		this.student_name = student_name;
	}


	public int getStudent_id() {
		return student_id;
	}


	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}


	public String getStudent_name() {
		return student_name;
	}


	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	
	
	
}
