package com.kathesama.app.master.microservices.service.card.application.ports.output;

import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;

import java.util.Optional;

public interface CardPersistenceOutputPort {
    Optional<CardEntity> findByMobileNumber(String mobileNumber);

    void deleteByCardId(Long cardId);

    /**
     *
     * @param card - AccountEntity Object
     */
    CardEntity save(CardEntity card);
}
