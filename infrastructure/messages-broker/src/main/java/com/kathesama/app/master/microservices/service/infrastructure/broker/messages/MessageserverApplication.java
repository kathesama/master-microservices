package com.kathesama.app.master.microservices.service.infrastructure.broker.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MessageserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageserverApplication.class, args);
	}
}