package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    Optional<LoanEntity> findByMobileNumber(String mobileNumber);

    Optional<LoanEntity> findByLoanNumber(String cardNumber);
}
