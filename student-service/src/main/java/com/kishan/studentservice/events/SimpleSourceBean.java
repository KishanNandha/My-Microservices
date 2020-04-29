package com.kishan.studentservice.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {
	private Source source;
	private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

	//Spring Cloud Stream will inject a Source interface implementation for use by the service
	@Autowired
	public SimpleSourceBean(Source source) {
		this.source = source;
	}

	
	//The Source interface is a Spring Cloud
	//defined interface that exposes a single method called output().
	//The Source interface is a convenient interface to use when your service only needs to publish to a single channel. 
	//The output() method returns a class of type MessageChannel. The
	//MessageChannel is how you’ll send messages to the message broker
	
	public void publishStudentChange(String action,int studentId ,String studentName) {
		logger.debug("Sending Kafka message {} for Student: {}", action, studentName);
		//The message to be published is a Java POJO.
		StudentChangeModel change = new StudentChangeModel(studentId,studentName,action);
		
		//When you’re ready to publish the message, use the send() method on the
		//MessageChannel class returned from the source.output() method
		//The send() method takes a Spring Message class. You use a Spring helper class
		//called MessageBuilder to take the contents of the OrganizationChangeModel
		//class and convert it to a Spring Message class.
		source.output()
			  .send(MessageBuilder.withPayload(change)
					  			  .build());
	}
}