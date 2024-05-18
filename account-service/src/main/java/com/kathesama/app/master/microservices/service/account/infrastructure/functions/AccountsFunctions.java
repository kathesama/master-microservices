package com.kathesama.app.master.microservices.service.account.infrastructure.functions;

import com.kathesama.app.master.microservices.service.account.application.ports.input.AccountServiceInputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountsFunctions {
    @Bean
    public Consumer<Long> updateCommunication(AccountServiceInputPort accountsService) {
        return accountNumber -> {
            log.info("Updating Communication status for the account number : " + accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }

}
