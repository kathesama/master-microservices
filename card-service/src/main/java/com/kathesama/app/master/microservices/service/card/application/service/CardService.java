package com.kathesama.app.master.microservices.service.card.application.service;

import com.kathesama.app.master.microservices.service.card.application.ports.input.CardServiceInputPort;
import com.kathesama.app.master.microservices.service.card.application.ports.output.CardPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.card.domain.model.Cards;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CardService implements CardServiceInputPort {
    private final CardPersistenceOutputPort cardPersistencePort;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public Cards save(String mobileNumber) {
        return null;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public Cards fetch(String mobileNumber) {
        return null;
    }

    /**
     * @param cardsDto - Cards Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public Cards update(Cards cardsDto) {
        return null;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     */
    @Override
    public void delete(String mobileNumber) {

    }
}
