package com.kathesama.app.master.microservices.common.util.common;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
    GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred."),
    PRODUCT_NOT_FOUND("ERR_PRODUCT_001", "Product not found."),
    RESOURCE_NOT_FOUND("ERR_RESOURCE_001", "Resource not found."),
    CUSTOMER_ALREADY_EXISTS("ERR_CUSTOMER_001", "Customer already exists."),
    USER_NOT_FOUND("ERR_USER_001", "User not found."),
    INVALID_PARAMS("ERR_HANDLER_001", "Invalid given parameters."),
    ACCESS_DENIED("ERR_ACCESS_001", "Access Denied or Forbidden");

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
