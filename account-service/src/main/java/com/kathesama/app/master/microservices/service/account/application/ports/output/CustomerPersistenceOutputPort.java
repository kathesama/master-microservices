package com.kathesama.app.master.microservices.service.account.application.ports.output;

import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerPersistenceOutputPort {
    /**
     *
     * @param customer - Customer Object
     */
    CustomerEntity save(CustomerEntity customer);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    Optional<CustomerEntity> findByMobileNumber(String mobileNumber);

//    Optional<CustomerEntity> findByCustomerId(Long id);

//    /**
//     *
//     * @param customerDto - Customer Object
//     * @return boolean indicating if the update of Account details is successful or not
//     */
//    boolean updateAccount(Customer customerDto);
//
//    /**
//     *
//     * @param mobileNumber - Input Mobile Number
//     * @return boolean indicating if the delete of Account details is successful or not
//     */
//    boolean deleteAccount(String mobileNumber);
}
