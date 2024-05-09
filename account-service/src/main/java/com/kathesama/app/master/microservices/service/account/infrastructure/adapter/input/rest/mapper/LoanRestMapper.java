package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.common.domain.model.Loan;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRestMapper {
    Loan toLoan(LoanResponseModel loanResponseModel);
}
