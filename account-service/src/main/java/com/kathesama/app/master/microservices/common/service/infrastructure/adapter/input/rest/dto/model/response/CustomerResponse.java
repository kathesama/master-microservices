package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    @JsonProperty("customerId")
    private Long customerId;
    private String name;
    private String email;

    @JsonProperty("mobileNumber")
    private String mobileNumber;
    private AccountResponse account;
}
