package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsResponse {
    @JsonProperty("customerId")
    private Long customerId;
    private String name;
    private String email;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    private AccountResponse account;
    private LoanResponseModel loan;
    private CardResponseModel card;
}
