package com.kishan.courseservice.feignProxy;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kishan.courseservice.beans.Student;

import feign.hystrix.FallbackFactory;

//This enables proxy creating for student service
//@FeignClient(name="student-service", url="localhost:8001")

//Hard coded URL was removed
//@FeignClient(name="student-service")
//Above connects directly to student service. We want to connect to zuul and zull will connect to student service
//To do this we wil connect feign to zuul proxy
//@FeignClient(name="zuul-server")
//To use Hystrix with fegin client
//@FeignClient(name="zuul-server",fallback = StudentServiceFeignProxyFallBack.class)
//To use exception handling with feign and hystrix
@FeignClient(name="zuul-server",fallbackFactory = StudentServiceFeignFactory.class)
//This enables Ribbon (Client Side Load Balancing)
@RibbonClient(name="student-service")
public interface StudentServiceFeignProxy {

	//@GetMapping("/students/course/{courseId}")
	//As we are connecting to zuul we need to append service name
	@GetMapping("student-service/students/course/{courseId}")
	public List<Student> retrieveStudentDetailsForCourse(@PathVariable int courseId);
	
	//The Spring Cloud framework will dynamically generate a proxy class that will be used to invoke the targeted REST service. There’s no code being written for calling the service other than an interface definition.
}

@Component
class StudentServiceFeignFactory implements FallbackFactory<StudentServiceFeignProxy> {

	@Override
	public StudentServiceFeignProxy create(Throwable cause) {
		
		return new StudentServiceFeignProxyFallBack(cause);
	}
	
}


//This class is used to server as fallback class for StudentServiceFeignProxy
class StudentServiceFeignProxyFallBack implements StudentServiceFeignProxy {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Throwable cause;
	
	public StudentServiceFeignProxyFallBack(Throwable cause) {
		super();
		this.cause = cause;
	}

	@Override
	public List<Student> retrieveStudentDetailsForCourse(int courseId) {
		
		if(null != cause) {
			logger.error("Error {}",cause.getLocalizedMessage());
		}
		
		Student student = new Student();
		student.setStudentId(0);
		student.setStudentName("This is demo student CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. \" +\r\n" + 
				"                    \" Service will be back shortly - ");
		return Arrays.asList(student);
	}
	
}
