package com.kathesama.app.master.microservices.service.account.application.service.fetch;

import com.kathesama.app.master.microservices.service.account.application.ports.input.LoansFeignRestClientInputPort;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Qualifier("loansFetchFallback")
public class LoansFetchFallback implements LoansFeignRestClientInputPort {
    @Override
    public ResponseEntity<LoanResponseModel> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
