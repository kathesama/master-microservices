package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence;

import com.kathesama.app.master.microservices.service.card.application.ports.output.CardPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardPersistenceAdapter implements CardPersistenceOutputPort {
    private final CardsRepository cardRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<CardEntity> findByMobileNumber(String mobileNumber) {
        log.info("FIND - Looking record for mobile number: {}", mobileNumber);
        return cardRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    @Transactional
    public void deleteByCardId(Long cardId) {
        log.info("DELETE - Looking record for card ID: {}", cardId.toString());

        cardRepository.deleteById(cardId);
    }


    /**
     * @param card - AccountEntity Object
     */
    @Override
    @Transactional
    public CardEntity save(CardEntity card) {
        log.info("SAVE - saving record for card: {}", card.getCardNumber());

        return cardRepository.save(card);
    }
}
