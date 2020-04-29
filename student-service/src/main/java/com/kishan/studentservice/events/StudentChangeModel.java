package com.kishan.studentservice.events;

public class StudentChangeModel {
	
	private int studentId;
	private String studentName;
	private String changeType;
	public StudentChangeModel(int studentId, String studentName, String changeType) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.changeType = changeType;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
}
