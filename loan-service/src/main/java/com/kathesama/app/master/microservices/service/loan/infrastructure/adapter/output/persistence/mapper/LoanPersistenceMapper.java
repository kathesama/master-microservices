package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.master.microservices.service.common.domain.model.Loan;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.output.persistence.entity.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanPersistenceMapper {
    LoanEntity toLoanEntity(Loan card);
    Loan toLoan(LoanEntity cardEntity);
    List<Loan> toLoans(List<LoanEntity> cardEntities);

    @Mapping(target = "loanId", ignore = true) // Ignorar el ID para evitar sobrescribirlo
    void updateLoanFromDto(Loan source, @MappingTarget LoanEntity target);
}
