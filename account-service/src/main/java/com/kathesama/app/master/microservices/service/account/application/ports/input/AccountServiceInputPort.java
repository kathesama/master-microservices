package com.kathesama.app.master.microservices.service.account.application.ports.input;

import com.kathesama.app.master.microservices.service.account.domain.model.Account;
import com.kathesama.app.master.microservices.service.account.domain.model.Customer;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request.AccountRequestModel;

public interface AccountServiceInputPort {
    /**
     *
     * @param customer - Customer Object
     */
    Customer createAccount(Customer customer);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    Customer fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto - Customer Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    Account updateAccount(Long customerId, AccountRequestModel customerDto);

    /**
     *
     * @param customerId - Input customerId
     */
    void deleteAccount(Long customerId);

    /**
     *
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    boolean updateCommunicationStatus(Long accountNumber);
}
