package com.kathesama.app.master.microservices.service.common.util.common;

import lombok.Getter;

@Getter
public enum SuccessCatalog {
    DEFAULT_ADDRESS("ADDRESS", "123 Main Street, New York"),
    SAVINGS("SAVINGS", "Savings account"),
    CREDIT_CARD("CREDIT_CARD", "1_00_000"),
    NEW_CARD_LIMIT("NEW_CARD_LIMIT", "Credit card"),
    STATUS_200("200", "Request processed successfully"),
    STATUS_201("201", "Created successfully"),
    MESSAGE_417_UPDATE("417", "Update operation failed. Please try again or contact Dev team"),
    MESSAGE_417_DELETE("417", "Delete operation failed. Please try again or contact Dev team"),
    STATUS_417("417", "Update operation failed. Please try again or contact Dev team");

    private final String code;
    private final String message;

    SuccessCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
