package com.kathesama.app.master.microservices.service.account.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private Account account;
}
