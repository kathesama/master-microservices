package com.kathesama.app.master.microservices.service.card.application.ports.input;

import com.kathesama.app.master.microservices.service.card.domain.model.Cards;

public interface CardServiceInputPort {
    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    Cards save(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    Cards fetch(String mobileNumber);

    /**
     *
     * @param cardsDto - Cards Object
     * @return boolean indicating if the update of card details is successful or not
     */
    Cards update(Cards cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     */
    void delete(String mobileNumber);
}
