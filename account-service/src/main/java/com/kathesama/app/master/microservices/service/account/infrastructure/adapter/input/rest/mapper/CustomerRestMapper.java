package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.account.domain.model.Customer;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request.CustomerRequestModel;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AccountRestMapper.class)
public interface CustomerRestMapper {
    Customer toCustomer(CustomerRequestModel customerRequestModel);
    CustomerResponse toCustomerResponse(Customer customerRequestModel);
}
