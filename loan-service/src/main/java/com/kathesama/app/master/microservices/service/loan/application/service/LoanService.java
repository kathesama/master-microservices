package com.kathesama.app.master.microservices.service.loan.application.service;

import com.kathesama.app.master.microservices.service.common.domain.exception.CustomerAlreadyExistsException;
import com.kathesama.app.master.microservices.service.common.domain.exception.ResourceNotFoundException;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
import com.kathesama.app.master.microservices.service.loan.application.ports.input.LoanServiceInputPort;
import com.kathesama.app.master.microservices.service.loan.application.ports.output.LoanPersistenceOutputPort;
import com.kathesama.app.master.microservices.service.common.domain.model.Loan;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.entity.LoanEntity;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.mapper.LoanPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService implements LoanServiceInputPort {
    private final LoanPersistenceOutputPort loanPersistencePort;
    private final LoanPersistenceMapper persistenceMapper;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public Loan createLoan(String mobileNumber) {
        return (Loan) loanPersistencePort.findByMobileNumber(mobileNumber)
            .map((loan) -> {
                throw new CustomerAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
            })
            .orElseGet(() ->
                    persistenceMapper.toLoan(
                            loanPersistencePort.save(
                                    persistenceMapper.toLoanEntity(createNewLoan(mobileNumber))
                            )
                    )
            );
    }


    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(SuccessCatalog.HOME_LOAN.getMessage());
        newLoan.setTotalLoan(Double.parseDouble(SuccessCatalog.NEW_LOAN_LIMIT.getMessage()));
        newLoan.setAmountPaid(0D);
        newLoan.setOutstandingAmount(Double.parseDouble(SuccessCatalog.NEW_LOAN_LIMIT.getMessage()));
        return newLoan;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public Loan fetch(String mobileNumber) {
        return persistenceMapper.toLoan(
                loanPersistencePort
                    .findByMobileNumber(mobileNumber)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                    )
        );
    }

    /**
     * @param loanDto - Loan Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public Loan update(String mobileNumber, Loan loanDto) {
        return loanPersistencePort
                .findByMobileNumber(mobileNumber)
                    .map(loan -> {
                        persistenceMapper.updateLoanFromDto(loanDto, loan);

                        return persistenceMapper.toLoan(
                                loanPersistencePort.save(loan)
                        );
                    })
                .orElseThrow(() ->
                    new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber())
                );
    }

    /**
     * @param mobileNumber - Input Mobile Number
     */
    @Override
    public void delete(String mobileNumber) {
        LoanEntity loanEntity = loanPersistencePort
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );

        loanPersistencePort.deleteByLoanId(loanEntity.getLoanId());
    }
}
