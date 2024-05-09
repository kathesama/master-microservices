package com.kathesama.app.master.microservices.service.account.domain.model;

import com.kathesama.app.master.microservices.service.common.domain.model.Card;
import com.kathesama.app.master.microservices.service.common.domain.model.Loan;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails extends Customer {
    private Card card;
    private Loan loan;
}
