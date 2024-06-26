package com.kathesama.app.master.microservices.infrastructure.server.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
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
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contactSupport"))
				).uri("lb://account-service"))
			.route(p -> p
				.path("/services/loans/api/v1/**")
				.filters(f -> f.rewritePath("/services/(?<segment>loans/api/v1(/.*)?)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.retry(retryConfig -> retryConfig.setRetries(3)
								.setMethods(HttpMethod.GET)
								.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true))
				)
				.uri("lb://loan-service"))
			.route(p -> p
				.path("/services/cards/api/v1/**")
				.filters(f -> f.rewritePath("/services/(?<segment>cards/api/v1(/.*)?)", "/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
								.setKeyResolver(userKeyResolver()))
				)
				.uri("lb://card-service"))
			.build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}
}