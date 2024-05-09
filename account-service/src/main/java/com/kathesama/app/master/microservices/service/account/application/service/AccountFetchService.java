package com.kathesama.app.master.microservices.service.account.application.service;

import com.kathesama.app.master.microservices.service.account.application.ports.input.AccountFetchServiceInputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.input.CardsFeignRestClientInputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.input.LoansFeignRestClientInputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.output.AccountPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.output.CustomerPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.account.domain.model.CustomerDetails;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.CardRestMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.CustomerDetailsRestMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.LoanRestMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.mapper.AccountPersistenceMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.kathesama.app.master.microservices.service.common.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFetchService implements AccountFetchServiceInputPort {
    private final AccountPersistenceOutputPort accountPersistencePort;
    private final AccountPersistenceMapper accountMapper;
    private final CustomerPersistenceOutputPort customerPersistencePort;
    private final CustomerPersistenceMapper customerMapper;

    private final CardsFeignRestClientInputPort cardsFeignRestClient;
    private final LoansFeignRestClientInputPort loansFeignRestClient;
    private final CustomerDetailsRestMapper customerDetailsMapper;
    private final LoanRestMapper loanMapper;
    private final CardRestMapper cardMapper;
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetails fetchCustomerDetails(String mobileNumber) {
        CustomerEntity customer = customerPersistencePort.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        CustomerDetails customerDetails = customerDetailsMapper.toCustomerDetails(customerMapper.toCustomer(customer));

        accountPersistencePort.findByCustomerId(customer.getCustomerId())
            .map(accountMapper::toAccount)
            .ifPresentOrElse(customerDetails::setAccount, () -> {
                throw new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString());
            });

        customerDetails.setLoan(loanMapper.toLoan(loansFeignRestClient.fetchCardDetails(mobileNumber).getBody()));
        customerDetails.setCard(cardMapper.toCard(cardsFeignRestClient.fetchCardDetails(mobileNumber).getBody()));

        return customerDetails;
    }
}
