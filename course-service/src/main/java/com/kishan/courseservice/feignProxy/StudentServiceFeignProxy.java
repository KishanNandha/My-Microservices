package com.kishan.courseservice.feignProxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kishan.courseservice.beans.Student;

//This enables proxy creating for student service
//@FeignClient(name="student-service", url="localhost:8001")

//Hard coded URL was removed
@FeignClient(name="student-service")
//This enables Ribbon (Client Side Load Balancing)
@RibbonClient(name="student-service")
public interface StudentServiceFeignProxy {

	@GetMapping("/students/course/{courseId}")
	public List<Student> retrieveStudentDetailsForCourse(@PathVariable int courseId);
	
	//The Spring Cloud framework will dynamically generate a proxy class that will be used to invoke the targeted REST service. There’s no code being written for calling the service other than an interface definition.
}
