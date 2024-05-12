package com.kathesama.app.master.microservices.service.account.application.service.fetch;

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
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
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

    public AccountFetchService(@Qualifier("cardsFeignRestClientInputPort") CardsFeignRestClientInputPort cardsFeignRestClient,
                               @Qualifier("loansFeignRestClientInputPort") LoansFeignRestClientInputPort loansFeignRestClient,
                               AccountPersistenceOutputPort accountPersistencePort,
                               AccountPersistenceMapper accountMapper,
                               CustomerPersistenceOutputPort customerPersistencePort,
                               CustomerPersistenceMapper customerMapper,
                               CustomerDetailsRestMapper customerDetailsMapper,
                               LoanRestMapper loanMapper,
                               CardRestMapper cardMapper) {
        this.accountPersistencePort = accountPersistencePort;
        this.accountMapper = accountMapper;
        this.customerPersistencePort = customerPersistencePort;
        this.customerMapper = customerMapper;
        this.cardsFeignRestClient = cardsFeignRestClient;
        this.loansFeignRestClient = loansFeignRestClient;
        this.customerDetailsMapper = customerDetailsMapper;
        this.loanMapper = loanMapper;
        this.cardMapper = cardMapper;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetails fetchCustomerDetails(String mobileNumber, String correlationId) {
        CustomerEntity customer = customerPersistencePort.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        CustomerDetails customerDetails = customerDetailsMapper.toCustomerDetails(customerMapper.toCustomer(customer));

        accountPersistencePort.findByCustomerId(customer.getCustomerId())
            .map(accountMapper::toAccount)
            .ifPresentOrElse(customerDetails::setAccount, () -> {
                throw new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString());
            });

        ResponseEntity<LoanResponseModel> loanFetchData = loansFeignRestClient.fetchCardDetails(correlationId, mobileNumber);
        if (loanFetchData != null){
            customerDetails.setLoan(loanMapper.toLoan(loanFetchData.getBody()));
        }

        ResponseEntity<CardResponseModel> cardFetchData = cardsFeignRestClient.fetchCardDetails(correlationId, mobileNumber);
        if (cardFetchData != null){
            customerDetails.setCard(cardMapper.toCard(cardFetchData.getBody()));
        }

        return customerDetails;
    }
}
