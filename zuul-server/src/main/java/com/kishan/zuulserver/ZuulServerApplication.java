package com.kishan.zuulserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
//Note: @EnableZuulServer is used when you want to build your own routing service and not use any Zuul prebuilt capabilities. An example of this would be if you wanted to use Zuul to integrate with a service discovery engine other than Eureka (for example, Consul).
//This is used to enable reverse proxy zuul
@EnableZuulProxy
public class ZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}

}
