package com.kathesama.app.master.microservices.service.account.application.service.fetch;

import com.kathesama.app.master.microservices.service.account.application.ports.input.CardsFeignRestClientInputPort;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Qualifier("cardsFetchFallback")
public class CardsFetchFallback implements CardsFeignRestClientInputPort{

    @Override
    public ResponseEntity<CardResponseModel> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
