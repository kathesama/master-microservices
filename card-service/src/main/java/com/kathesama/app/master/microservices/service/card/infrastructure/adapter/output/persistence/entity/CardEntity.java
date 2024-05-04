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
@Table(name = "cards")
public class CardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long cardId;

//    @Column(name="mobileNumber")
    private String mobileNumber;

//    @Column(name="cardNumber")
    private String cardNumber;

//    @Column(name="cardType")
    private String cardType;

//    @Column(name="totalLimit")
    private Double totalLimit;

//    @Column(name="amountUsed")
    private Double amountUsed;

//    @Column(name="availableAmount")
    private Double availableAmount;
}
