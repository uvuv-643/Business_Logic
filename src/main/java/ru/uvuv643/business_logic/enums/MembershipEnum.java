package ru.uvuv643.business_logic.enums;

public enum MembershipEnum {
    BASIC("basic", 10.0, 30.0),
    PRO("pro", 20.0, 100.0),
    VIP("vip", 30.0, 200.0);

    private final String code;
    private final double priceInUsd;
    private final double coins;

    MembershipEnum(String code, double priceInUsd, double coins) {
        this.code = code;
        this.priceInUsd = priceInUsd;
        this.coins = coins;
    }

    public String getCode() {
        return this.code;
    }

    public double getPriceInUsd() {
        return priceInUsd;
    }

    public double getCoins() {
        return coins;
    }
}
