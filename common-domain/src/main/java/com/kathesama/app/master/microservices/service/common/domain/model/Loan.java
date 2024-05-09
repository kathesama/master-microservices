package com.kathesama.app.master.microservices.service.common.domain.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private Long loanId;

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    private Double totalLoan;

    private Double amountPaid;

    private Double outstandingAmount;

}
