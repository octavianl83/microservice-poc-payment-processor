package com.logicore.rest.services.servicetransformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceTransformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTransformationApplication.class, args);
	}

}
