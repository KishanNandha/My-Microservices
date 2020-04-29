package com.kishan.courseservice.redis;

import java.util.List;

import com.kishan.courseservice.beans.Student;

public interface StudentCacheManager {
	void cacheStudnetDetails(String courseName,List<Student> students);
	List<Student> getCachedStudentsForCourse(String courseName);
}
