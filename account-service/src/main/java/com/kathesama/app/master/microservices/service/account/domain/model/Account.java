package com.kathesama.app.master.microservices.service.account.domain.model;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long customerId;
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private Boolean communicationSw;
}
