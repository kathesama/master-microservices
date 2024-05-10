package com.kathesama.app.master.microservices.infrastructure.server.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(p -> p
				.path("/services/accounts/api/v1/**", "/services/customers/api/v1/**")
				.filters(f -> f.rewritePath("/services/(?<segment>(accounts|customers)/api/v1(/.*)?)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				.uri("lb://account-service"))
			.route(p -> p
				.path("/services/cards/api/v1/**")
				.filters(f -> f.rewritePath("/services/(?<segment>cards/api/v1(/.*)?)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				.uri("lb://card-service"))
			.route(p -> p
				.path("/services/loans/api/v1/**")
				.filters(f -> f.rewritePath("/services/(?<segment>loans/api/v1(/.*)?)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				.uri("lb://loan-service"))
			.build();
	}
}