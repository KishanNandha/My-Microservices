package com.kishan.courseservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.kishan.courseservice.beans.CourseResponseBean;
import com.kishan.courseservice.beans.Student;
import com.kishan.courseservice.beans.StudentResponseBean;
import com.kishan.courseservice.feignProxy.StudentServiceFeignProxy;
import com.kishan.courseservice.model.Course;
import com.kishan.courseservice.redis.StudentCacheManager;
import com.kishan.courseservice.repository.CourseRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CourseService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private StudentServiceFeignProxy studentServiceFeignProxy;
	
	@Autowired
	private StudentCacheManager studentCacheManager;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public CourseResponseBean getCourseDetails(String courseName) {
		CourseResponseBean responseBean = new CourseResponseBean();
		Course course = courseRepo.findByCourseName(courseName);
		if(null != course) {
			responseBean.setCourseId(course.getCourseId());
			responseBean.setCourseName(course.getCourseName());
			responseBean.setCourseDesc(course.getCourseDesc());
			
			//This was Spring rest tempate code now replaced with Feign Rest Client
			//RestTemplate restTemplate = new RestTemplate();
			//Student[] students = restTemplate.getForObject("http://localhost:8001/students/course/{courseId}", Student[].class,course.getCourseId());
			
			List<Student> students = getStudentsForCourse(course);
			
			if(null != students) {
				List<StudentResponseBean> studentResponseBean = 
						 //Arrays.asList(students)
						students
						.stream()
						.map(student -> new StudentResponseBean(student.getStudentId(), student.getStudentName(),student.getPort()))
						.collect(Collectors.toList());
				responseBean.setStudents(studentResponseBean);
			}
			
			String port = environment.getProperty("local.server.port");
			responseBean.setCourseServicePort(port);
		}
		return responseBean;
	}
	
	private List<Student> getStudentsForCourse(Course course) {
		
		//Get the data from cache
		List<Student> cachedStudents = studentCacheManager.getCachedStudentsForCourse(course.getCourseName());
		if(null == cachedStudents) {
			logger.info("Not able to get data from cache. calling service");
			cachedStudents = callStudentService(course);
			studentCacheManager.cacheStudnetDetails(course.getCourseName(), cachedStudents);
		}
		else {
			logger.info("Getting student list from cache");
		}
		return cachedStudents;
	}
	
	//Hystrix and Spring Cloud use the @HystrixCommand annotation to mark Java class methods as being managed by a Hystrix circuit breaker. 
	//When the Spring framework sees the @HystrixCommand, it will dynamically generate a proxy that will wrapper the method and manage all calls to that method through a thread pool of threads specifically set aside to handle remote calls.
	@HystrixCommand(
			//The commandProperties attribute accepts an array of HystrixProperty objects that can pass in custom properties to configure the Hystrix circuit breaker.
			commandProperties=
				{	
					//the execution.isolation.thread.timeoutInMilliseconds property to set the maximum timeout a Hystrix call will wait before failing to be 12 seconds.	
					@HystrixProperty(
					name="execution.isolation.thread.timeoutInMilliseconds",
					value="12000")
				},
			
			//This attribute will contain the name of a method that will be called when Hystrix has to interrupt a call because it’s taking too long.
			fallbackMethod = "getFallbackStuddentList"	
			)
	private List<Student> callStudentService(Course course) {
		//randomlyRunLong();
		return studentServiceFeignProxy.retrieveStudentDetailsForCourse(course.getCourseId());
	}
	
	
	//The fallback method must have the exact same method signature as the originating function as all of the parameters passed into the original method protected by the @HystrixCommand will be passed to the fallback.
	public List<Student> getFallbackStuddentList(Course course) {
		Student student = new Student();
		student.setStudentId(0);
		student.setStudentName("This is demo student CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. \" +\r\n" + 
				"                    \" Service will be back shortly - ");
		return Arrays.asList(student);
	}
	
	public void refreshStudentCacheData() {
		//currently hard coding course data
		Course course = new Course();
		course.setCourseId(1);
		course.setCourseName("Java");
		List<Student> students = callStudentService(course);
		studentCacheManager.cacheStudnetDetails(course.getCourseName(), students);
	}
	
	/**
	 * This method is used to generate delay(not every time but randomly)
	 */
	private void randomlyRunLong() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		if (randomNum == 3)
			sleep();
	}

	private void sleep() {
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
