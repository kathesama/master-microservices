package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence;

import com.kathesama.app.master.microservices.common.service.application.ports.output.AccountPersistenceOutputPort;
import com.kathesama.app.master.microservices.common.service.application.ports.output.CustomerPersistenceOutputPort;
import com.kathesama.app.master.microservices.common.service.domain.model.Customer;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.AccountEntity;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.repository.AccountRepository;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPersistenceOutputPort, CustomerPersistenceOutputPort {
    private final AccountRepository accountsRepository;
    private final CustomerRepository customerRepository;

    /**
     * @param customer - Customer Object
     */
    @Override
    @Transactional
    public CustomerEntity save(CustomerEntity customer) {
        log.info("Saving record for mobile number: {}", customer.getMobileNumber());

        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findByMobileNumber(String mobileNumber) {
        log.info("looking record for mobile number: {}", mobileNumber);

        return customerRepository
            .findByMobileNumber(mobileNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountEntity> findByCustomerId(Long customerId) {
        log.info("looking record for ID number: {}", customerId);

        return accountsRepository
                .findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void deleteByCustomerId(Long customerId) {
        accountsRepository.deleteByCustomerId(customerId);
    }

    /**
     * @param account - AccountEntity Object
     */
    @Override
    @Transactional
    public AccountEntity save(AccountEntity account) {
        log.info("Saving record for mobile number: {}", account.getCustomerId());

        return accountsRepository.save(account);
    }
}
