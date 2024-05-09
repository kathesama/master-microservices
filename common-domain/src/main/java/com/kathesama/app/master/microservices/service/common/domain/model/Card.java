package com.kathesama.app.master.microservices.service.common.domain.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Long cardId;

    private String mobileNumber;

    private String cardNumber;

    private String cardType;

    private Double totalLimit;

    private Double amountUsed;

    private Double availableAmount;
}
