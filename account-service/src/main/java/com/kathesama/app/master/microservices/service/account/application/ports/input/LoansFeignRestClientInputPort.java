package com.kathesama.app.master.microservices.service.account.application.ports.input;

import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "loan-service")
public interface LoansFeignRestClientInputPort {
    @GetMapping(value = "/loans/api/v1/{mobileNumber}", consumes = "application/json")
    ResponseEntity<LoanResponseModel> fetchCardDetails(@PathVariable String mobileNumber);
}
