package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseModel {
    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("loanNumber")
    private String loanNumber;

    @JsonProperty("loanType")
    private String loanType;

    @JsonProperty("totalLoan")
    private Double totalLoan;

    @JsonProperty("amountPaid")
    private Double amountPaid;

    @JsonProperty("outstandingAmount")
    private Double outstandingAmount;
}
