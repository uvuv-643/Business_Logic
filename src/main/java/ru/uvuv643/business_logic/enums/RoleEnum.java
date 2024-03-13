package ru.uvuv643.business_logic.enums;

public enum RoleEnum {

    ADMIN("admin"),
    USER("user");

    private final String code;

    RoleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
