package com.kathesama.app.master.microservices.service.loan.application.ports.input;

import com.kathesama.app.master.microservices.service.loan.domain.model.Loan;

public interface LoanServiceInputPort {
    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    Loan createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return LoanEntity Details based on a given mobileNumber
     */
    Loan fetch(String mobileNumber);

    /**
     *
     * @param loanDto - Loan Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    Loan update(String mobileNumber, Loan loanDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     */
    void delete(String mobileNumber);
}
