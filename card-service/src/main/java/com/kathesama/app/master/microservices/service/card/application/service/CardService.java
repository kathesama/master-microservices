package com.kathesama.app.master.microservices.service.card.application.service;

import com.kathesama.app.master.microservices.service.card.application.ports.input.CardServiceInputPort;
import com.kathesama.app.master.microservices.service.card.application.ports.output.CardPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.card.domain.model.Card;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.mapper.CardPersistenceMapper;
import com.kathesama.app.master.microservices.service.common.domain.exception.CustomerAlreadyExistsException;
import com.kathesama.app.master.microservices.service.common.domain.exception.ResourceNotFoundException;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class CardService implements CardServiceInputPort {
    private final CardPersistenceOutputPort cardPersistencePort;
    private final CardPersistenceMapper persistenceMapper;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public Card createCard(String mobileNumber) {
        return (Card) cardPersistencePort.findByMobileNumber(mobileNumber)
            .map((card) -> {
                throw new CustomerAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
            })
            .orElseGet(() ->
                    persistenceMapper.toCard(
                            cardPersistencePort.save(
                                    persistenceMapper.toCardEntity(createNewCard(mobileNumber))
                            )
                    )
            );
    }


    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(SuccessCatalog.CREDIT_CARD.getMessage());
        newCard.setTotalLimit(Double.parseDouble(SuccessCatalog.NEW_CARD_LIMIT.getMessage()));
        newCard.setAmountUsed(0D);
        newCard.setAvailableAmount(Double.parseDouble(SuccessCatalog.NEW_CARD_LIMIT.getMessage()));
        return newCard;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public Card fetch(String mobileNumber) {
        return persistenceMapper.toCard(
                cardPersistencePort
                    .findByMobileNumber(mobileNumber)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
                    )
        );
    }

    /**
     * @param cardDto - Card Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public Card update(String mobileNumber, Card cardDto) {
        return cardPersistencePort
                .findByMobileNumber(mobileNumber)
                    .map(card -> {
                        persistenceMapper.updateCardFromDto(cardDto, card);

                        return persistenceMapper.toCard(
                                cardPersistencePort.save(card)
                        );
                    })
                .orElseThrow(() ->
                    new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber())
                );
    }

    /**
     * @param mobileNumber - Input Mobile Number
     */
    @Override
    public void delete(String mobileNumber) {
        CardEntity cardEntity = cardPersistencePort
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
                );

        cardPersistencePort.deleteByCardId(cardEntity.getCardId());
    }
}
