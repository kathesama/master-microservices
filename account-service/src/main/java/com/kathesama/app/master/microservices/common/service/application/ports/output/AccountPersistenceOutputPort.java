package com.kathesama.app.master.microservices.common.service.application.ports.output;

import com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity.AccountEntity;
import java.util.Optional;

public interface AccountPersistenceOutputPort {
    Optional<AccountEntity> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);

    /**
     *
     * @param account - AccountEntity Object
     */
    AccountEntity save(AccountEntity account);
}
