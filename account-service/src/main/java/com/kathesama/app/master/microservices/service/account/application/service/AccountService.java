package com.kathesama.app.master.microservices.service.account.application.service;

import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.AccountMessage;
import com.kathesama.app.master.microservices.service.common.domain.exception.CustomerAlreadyExistsException;
import com.kathesama.app.master.microservices.service.common.domain.exception.ResourceNotFoundException;
import com.kathesama.app.master.microservices.service.account.application.ports.input.AccountServiceInputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.output.AccountPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.output.CustomerPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.account.domain.model.Account;
import com.kathesama.app.master.microservices.service.account.domain.model.Customer;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request.AccountRequestModel;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.entity.AccountEntity;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.mapper.AccountPersistenceMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.repository.AccountRepository;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceInputPort {
    private final AccountPersistenceOutputPort accountPersistencePort;
    private final AccountPersistenceMapper accountMapper;

    private final CustomerPersistenceOutputPort customerPersistencePort;
    private final CustomerPersistenceMapper customerMapper;

    private final StreamBridge streamBridge;

    @Override
    public Customer createAccount(Customer customer) {
      Optional<CustomerEntity> optionalCustomer = customerPersistencePort.findByMobileNumber(customer.getMobileNumber());

      if (optionalCustomer.isPresent()){
          throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                  + customer.getMobileNumber());
      }

      CustomerEntity customerEntity = customerPersistencePort.save(customerMapper.toCustomerEntity(customer));
      AccountEntity accountEntity = accountPersistencePort.save(createNewAccount(customerEntity));

      this.sendCommunication(accountMapper.toAccount(accountEntity), customerMapper.toCustomer(customerEntity));
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

    private void sendCommunication(Account account, Customer customer) {
        AccountMessage accountsMsgDto = new AccountMessage(
                account.getAccountNumber(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobileNumber()
        );

        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    /**
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        if (accountNumber == null) {
            return false;
        }

        AccountEntity accountEntity = accountPersistencePort
                .findByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));

        accountEntity.setCommunicationSw(true);
        accountPersistencePort.save(accountEntity);

        return  true;
    }
}
