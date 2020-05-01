package com.kishan.studentservice.service;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.kishan.studentservice.model.Student;
import com.kishan.studentservice.repository.StudentRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private Environment environment;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
				})
	
	public List<Student> getStudentsByCourseId(int courseId) {
		//This method will set circuit breaker 
		//randomlyRunLong();
		logger.info("Before calling database");
		List<Student> students = getStudentsFormDb(courseId);
		String port = environment.getProperty("local.server.port");
		students.forEach(studnet -> {studnet.setPort(port);});
		return students;
	}

	@NewSpan(name="mysql-db")
	private List<Student> getStudentsFormDb(int courseId) {
		return studentRepo.findByCourseId(courseId);
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
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
