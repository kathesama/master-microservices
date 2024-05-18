package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response;

/**
 * @param accountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountMessage (
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
){}