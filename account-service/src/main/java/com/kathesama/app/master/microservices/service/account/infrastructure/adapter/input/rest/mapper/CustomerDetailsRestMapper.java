package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.account.domain.model.Customer;
import com.kathesama.app.master.microservices.service.account.domain.model.CustomerDetails;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request.CustomerDetailsRequestModel;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.CustomerDetailsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AccountRestMapper.class, LoanRestMapper.class, CardRestMapper.class} )
public interface CustomerDetailsRestMapper {
    Customer toCustomer(CustomerDetailsRequestModel customerRequestModel);
    CustomerDetails toCustomerDetails(Customer customerRequestModel);
    CustomerDetailsResponse toCustomerDetailsResponse(CustomerDetails customerRequestModel);
}
