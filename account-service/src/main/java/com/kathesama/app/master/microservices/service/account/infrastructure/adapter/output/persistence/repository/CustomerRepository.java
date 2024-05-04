package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByMobileNumber(String mobileNumber);
    Optional<CustomerEntity> findByCustomerId(Long id);
}
