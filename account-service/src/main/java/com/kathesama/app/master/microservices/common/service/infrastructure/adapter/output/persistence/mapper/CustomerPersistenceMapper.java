package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.master.microservices.common.service.domain.model.Customer;
import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {
    CustomerEntity toCustomerEntity(Customer customer);
    Customer toCustomer(CustomerEntity customerEntity);
    List<Customer> toCustomers(List<CustomerEntity> customerEntities);
}
