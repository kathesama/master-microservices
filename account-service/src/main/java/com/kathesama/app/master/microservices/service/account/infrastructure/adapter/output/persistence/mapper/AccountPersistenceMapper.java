package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.master.microservices.service.account.domain.model.Account;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    AccountEntity toAccountEntity(Account account);
    Account toAccount(AccountEntity accountEntity);
    List<Account> toAccounts(List<AccountEntity> accountEntities);
}
