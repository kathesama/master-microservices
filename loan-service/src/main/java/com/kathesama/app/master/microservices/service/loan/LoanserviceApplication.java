package com.kathesama.app.master.microservices.service.loan;

import com.kathesama.app.master.microservices.service.loan.util.LoansContactInfo;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = {LoansContactInfo.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loan microservice REST API Documentation",
				description = "EazyBank Loan microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Katherine Aguirre",
						email = "kathesama@gmail.com.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.eazybytes.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank Loan microservice REST API Documentation",
				url = "https://www.eazybytes.com/swagger-ui.html"
		)
)
public class LoanserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanserviceApplication.class, args);
	}
}