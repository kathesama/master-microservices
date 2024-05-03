package com.kathesama.app.master.microservices.common.service.application.service;

import com.kathesama.app.master.microservices.common.domain.exception.CustomerAlreadyExistsException;
import com.kathesama.app.master.microservices.common.domain.exception.ResourceNotFoundException;
import com.kathesama.app.master.microservices.common.service.application.ports.input.AccountServiceInputPort;
import com.kathesama.app.master.microservices.common.service.application.ports.output.AccountPersistenceOutputPort;
import com.kathesama.app.master.microservices.common.service.application.ports.output.CustomerPersistenceOutputPort;
import com.kathesama.app.master.microservices.common.service.domain.model.Account;
import com.kathesama.app.master.microservices.common.service.domain.model.Customer;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.request.AccountRequestModel;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.AccountEntity;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.mapper.AccountPersistenceMapper;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.repository.AccountRepository;
import com.kathesama.app.master.microservices.common.util.common.SuccessCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceInputPort {
    private final AccountRepository accountRepository;
    private final AccountPersistenceOutputPort accountPersistencePort;
    private final AccountPersistenceMapper accountMapper;

    private final CustomerPersistenceOutputPort customerPersistencePort;
    private final CustomerPersistenceMapper customerMapper;

    @Override
    public Customer createAccount(Customer customer) {
      Optional<CustomerEntity> optionalCustomer = customerPersistencePort.findByMobileNumber(customer.getMobileNumber());

      if (optionalCustomer.isPresent()){
          throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                  + customer.getMobileNumber());
      }

      CustomerEntity customerEntity = customerMapper.toCustomerEntity(customer);

//      customerEntity.setCreatedAt(LocalDateTime.now());
//      customerEntity.setUpdatedAt(LocalDateTime.now());
//      customerEntity.setCreatedBy("unknown");
//      customerEntity.setUpdatedBy("unknown");

      customerEntity = customerPersistencePort.save(customerEntity);

      accountPersistencePort.save(createNewAccount(customerEntity));

      return customerMapper.toCustomer(customerEntity);
    }

    @Override
    public Customer fetchAccount(String mobileNumber) {
        CustomerEntity optionalCustomer = customerPersistencePort
                .findByMobileNumber(mobileNumber)
                .orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        AccountEntity accountEntity = accountPersistencePort
                .findByCustomerId(optionalCustomer.getCustomerId())
                .orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", optionalCustomer.getCustomerId().toString()));

        Customer customer = customerMapper.toCustomer(optionalCustomer);
        customer.setAccount(accountMapper.toAccount(accountEntity));

        return customer;
    }

    @Override
    public Account updateAccount(Long customerId, AccountRequestModel accountRequestModel) {
        return accountPersistencePort
                .findByCustomerId(customerId)
                .map((account) -> {
                    account.setAccountType(accountRequestModel.getAccountType());
                    account.setBranchAddress(accountRequestModel.getBranchAddress());

                    return accountMapper.toAccount(accountPersistencePort.save(account));
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account", "customerId", customerId.toString()));

    }

    @Override
    public void deleteAccount(Long customerId) {
        if (accountPersistencePort.findByCustomerId(customerId).isEmpty()){
            throw new ResourceNotFoundException("Account", "customerId", customerId.toString());
        }

        accountPersistencePort.deleteByCustomerId(customerId);
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private AccountEntity createNewAccount(CustomerEntity customer) {
        AccountEntity newAccount = new AccountEntity();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(SuccessCatalog.SAVINGS.getCode());
        newAccount.setBranchAddress(SuccessCatalog.DEFAULT_ADDRESS.getCode());

        return newAccount;
    }
}
