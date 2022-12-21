package com.logicore.rest.services.servicevalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ServiceProcessor4Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProcessor4Application.class, args);
	}

}
