package com.kathesama.app.master.microservices.service.infrastructure.broker.messages.infrastructure.functions;

import com.kathesama.app.master.microservices.service.infrastructure.broker.messages.domain.model.AccountsMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class MessageFunctions {

    @Bean
    public Function<AccountsMessages, AccountsMessages> email() {
        return accountsMsgDto -> {
            log.info("Sending email with the details : " +  accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMessages,Long> sms() {
        return accountsMsgDto -> {
            log.info("Sending sms with the details : " +  accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        };
    }
}

