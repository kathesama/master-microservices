package com.kathesama.app.master.microservices.common.service.domain.model;


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
}
