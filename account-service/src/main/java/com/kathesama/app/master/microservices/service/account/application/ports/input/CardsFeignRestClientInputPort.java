package com.kathesama.app.master.microservices.service.account.application.ports.input;

import com.kathesama.app.master.microservices.service.account.application.service.fetch.CardsFetchFallback;
import com.kathesama.app.master.microservices.service.common.util.common.ConstantsCatalog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import org.springframework.web.bind.annotation.RequestHeader;

@Qualifier("cardsFeignRestClientInputPort")
@FeignClient(name = "card-service", fallback = CardsFetchFallback.class)
public interface CardsFeignRestClientInputPort {
    @GetMapping(value = "/cards/api/v1/{mobileNumber}", consumes = "application/json")
    ResponseEntity<CardResponseModel> fetchCardDetails(
            @RequestHeader(ConstantsCatalog.SERVICE_HEADER_CORRELATION_ID) String correlationId,
            @PathVariable String mobileNumber
    );
}