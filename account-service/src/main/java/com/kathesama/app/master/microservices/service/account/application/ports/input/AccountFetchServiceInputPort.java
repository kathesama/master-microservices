package com.kathesama.app.master.microservices.service.account.application.ports.input;

import com.kathesama.app.master.microservices.service.account.domain.model.CustomerDetails;

public interface AccountFetchServiceInputPort {
    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetails fetchCustomerDetails(String mobileNumber);
}
