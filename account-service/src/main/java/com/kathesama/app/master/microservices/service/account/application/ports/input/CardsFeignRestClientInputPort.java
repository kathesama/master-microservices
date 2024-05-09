package com.kathesama.app.master.microservices.service.account.application.ports.input;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;

@FeignClient(name = "card-service")
public interface CardsFeignRestClientInputPort {
    @GetMapping(value = "/cards/api/v1/{mobileNumber}", consumes = "application/json")
    ResponseEntity<CardResponseModel> fetchCardDetails(@PathVariable String mobileNumber);
}
