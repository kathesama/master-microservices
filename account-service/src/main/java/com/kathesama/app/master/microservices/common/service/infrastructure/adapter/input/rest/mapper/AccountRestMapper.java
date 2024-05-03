package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.common.service.domain.model.Account;
import com.kathesama.app.master.microservices.common.service.domain.model.Customer;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.request.CustomerRequestModel;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.response.AccountResponse;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRestMapper {
    AccountResponse toAccountResponse(Account account);
}
