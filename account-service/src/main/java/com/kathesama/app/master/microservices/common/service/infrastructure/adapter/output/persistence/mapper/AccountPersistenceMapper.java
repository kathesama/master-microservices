package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.master.microservices.common.service.domain.model.Account;
import com.kathesama.app.master.microservices.common.service.domain.model.Customer;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.AccountEntity;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    AccountEntity toAccountEntity(Account account);
    Account toAccount(AccountEntity accountEntity);
    List<Account> toAccounts(List<AccountEntity> accountEntities);
}
