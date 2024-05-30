package com.kathesama.app.master.microservices.service.infrastructure.broker.rabbitmq;

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