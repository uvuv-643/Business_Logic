package ru.uvuv643.business_logic.enums;

public enum ModerationStatusEnum {
    ACTIVE("active"),
    REJECTED("rejected"),
    ACCEPTED("accepted");

    private final String code;

    ModerationStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
