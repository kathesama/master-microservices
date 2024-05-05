package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.loan.domain.model.Loan;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.dto.model.request.LoanRequestModel;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRestMapper {
    LoanResponseModel toLoanResponse(Loan account);
    Loan toLoan(LoanRequestModel cardRequestModel);
}
