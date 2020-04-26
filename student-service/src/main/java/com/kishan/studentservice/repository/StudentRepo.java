package com.kishan.studentservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kishan.studentservice.model.Student;

@Repository
public interface StudentRepo extends CrudRepository<Student, Integer> {

	List<Student> findByCourseId(int courseId);
}
