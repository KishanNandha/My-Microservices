package com.kishan.courseservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kishan.courseservice.model.Course;

@Repository
public interface CourseRepo extends CrudRepository<Course, Integer> {

	Course findByCourseName(String courseName);
}
