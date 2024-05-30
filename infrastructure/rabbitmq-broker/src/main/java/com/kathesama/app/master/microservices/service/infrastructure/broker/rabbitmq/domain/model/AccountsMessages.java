package com.kathesama.app.master.microservices.service.infrastructure.broker.rabbitmq.domain.model;

/**
 * @param accountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountsMessages(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
) {}
