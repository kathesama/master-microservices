package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence;

import com.kathesama.app.master.microservices.service.loan.application.ports.output.LoanPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.entity.LoanEntity;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanPersistenceAdapter implements LoanPersistenceOutputPort {
    private final LoanRepository loanRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanEntity> findByMobileNumber(String mobileNumber) {
        log.info("FIND - Looking record for mobile number: {}", mobileNumber);
        return loanRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    @Transactional
    public void deleteByLoanId(Long loanId) {
        log.info("DELETE - Looking record for loan ID: {}", loanId.toString());

        loanRepository.deleteById(loanId);
    }


    /**
     * @param loan - AccountEntity Object
     */
    @Override
    @Transactional
    public LoanEntity save(LoanEntity loan) {
        log.info("SAVE - saving record for loan: {}", loan.getLoanNumber());

        return loanRepository.save(loan);
    }
}
