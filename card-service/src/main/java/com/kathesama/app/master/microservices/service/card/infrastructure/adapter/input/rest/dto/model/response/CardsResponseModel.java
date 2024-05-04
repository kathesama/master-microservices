package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.dto.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardsResponseModel {

    @JsonProperty("cardId")
    private Long cardId;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("cardNumber")
    private String cardNumber;

    @JsonProperty("cardType")
    private String cardType;

    @JsonProperty("totalLimit")
    private int totalLimit;

    @JsonProperty("amountUsed")
    private int amountUsed;

    @JsonProperty("availableAmount")
    private int availableAmount;
}
