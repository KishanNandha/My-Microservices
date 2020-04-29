package com.kishan.studentservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
//This is used to enable Open Feign
@EnableFeignClients
//This tells spring that you are going to use hystrix
@EnableCircuitBreaker
//This is to enable Hystrix Dashboard
@EnableHystrixDashboard
//The @EnableBinding annotation tells Spring Cloud Stream to bind the application to a message broker
@EnableBinding(Source.class)
public class StudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
	}

}
