package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity;

import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.output.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cardId")
    private Long cardId;

    @Column(name="mobileNumber")
    private String mobileNumber;

    @Column(name="cardNumber")
    private String cardNumber;

    @Column(name="cardType")
    private String cardType;

    @Column(name="totalLimit")
    private int totalLimit;

    @Column(name="amountUsed")
    private int amountUsed;

    @Column(name="availableAmount")
    private int availableAmount;
}
