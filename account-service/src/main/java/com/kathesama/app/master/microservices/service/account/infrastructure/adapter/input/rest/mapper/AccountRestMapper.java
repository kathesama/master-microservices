package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.account.domain.model.Account;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRestMapper {
    AccountResponse toAccountResponse(Account account);
}
