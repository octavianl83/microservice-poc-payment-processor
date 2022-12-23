package com.logicore.rest.services.serviceflowprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableFeignClients
@SpringBootApplication
public class ServiceFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFlowApplication.class, args);
	}

}
