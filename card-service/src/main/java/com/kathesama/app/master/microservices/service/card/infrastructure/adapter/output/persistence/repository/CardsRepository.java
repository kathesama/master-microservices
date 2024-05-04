package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByMobileNumber(String mobileNumber);

    Optional<CardEntity> findByCardNumber(String cardNumber);
}
