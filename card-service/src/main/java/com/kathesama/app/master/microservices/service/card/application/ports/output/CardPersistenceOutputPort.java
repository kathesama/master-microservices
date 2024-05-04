package com.kathesama.app.master.microservices.service.card.application.ports.output;

import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;

import java.util.Optional;

public interface CardPersistenceOutputPort {
    Optional<CardEntity> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);

    /**
     *
     * @param card - AccountEntity Object
     */
    CardEntity save(CardEntity card);
}
