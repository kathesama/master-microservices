package com.kathesama.app.master.microservices.service.common.util.common;

import lombok.Getter;

@Getter
public enum RolesCatalog {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String name;
    private final String simpleName;

    RolesCatalog(String code) {
        this.name = code;
        this.simpleName = code.substring(5);
    }
}
