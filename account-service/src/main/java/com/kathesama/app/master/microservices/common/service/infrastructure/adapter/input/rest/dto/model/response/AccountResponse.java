package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("accountNumber")
    private Long accountNumber;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("branchAddress")
    private String branchAddress;
}
