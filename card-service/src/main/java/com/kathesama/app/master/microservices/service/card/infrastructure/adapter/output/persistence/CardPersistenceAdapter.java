package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence;

import com.kathesama.app.master.microservices.service.card.application.ports.output.CardPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;

import java.util.Optional;

public class CardPersistenceAdapter implements CardPersistenceOutputPort {
    @Override
    public Optional<CardEntity> findByCustomerId(Long customerId) {
        return Optional.empty();
    }

    @Override
    public void deleteByCustomerId(Long customerId) {

    }

    /**
     * @param card - AccountEntity Object
     */
    @Override
    public CardEntity save(CardEntity card) {
        return null;
    }
}
