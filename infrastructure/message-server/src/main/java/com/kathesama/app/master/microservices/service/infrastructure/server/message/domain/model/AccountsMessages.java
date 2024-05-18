package com.kathesama.app.master.microservices.service.infrastructure.server.message.domain.model;

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
