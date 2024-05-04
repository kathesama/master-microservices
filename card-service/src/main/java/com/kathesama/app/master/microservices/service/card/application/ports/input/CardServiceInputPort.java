package com.kathesama.app.master.microservices.service.card.application.ports.input;

import com.kathesama.app.master.microservices.service.card.domain.model.Card;

public interface CardServiceInputPort {
    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    Card createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    Card fetch(String mobileNumber);

    /**
     *
     * @param cardDto - Card Object
     * @return boolean indicating if the update of card details is successful or not
     */
    Card update(String mobileNumber, Card cardDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     */
    void delete(String mobileNumber);
}
