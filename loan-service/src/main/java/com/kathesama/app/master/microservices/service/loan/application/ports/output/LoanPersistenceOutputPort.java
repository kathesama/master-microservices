package com.kathesama.app.master.microservices.service.loan.application.ports.output;


import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.entity.LoanEntity;

import java.util.Optional;

public interface LoanPersistenceOutputPort {
    Optional<LoanEntity> findByMobileNumber(String mobileNumber);

    void deleteByLoanId(Long cardId);

    /**
     *
     * @param card - AccountEntity Object
     */
    LoanEntity save(LoanEntity card);
}
