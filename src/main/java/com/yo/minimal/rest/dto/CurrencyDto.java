package com.yo.minimal.rest.dto;

import java.math.BigDecimal;

public class CurrencyDto {
    private String [] defaultCurrency;
    private BigDecimal priceForeignDefault;

    public String[] getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String[] defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public BigDecimal getPriceForeignDefault() {
        return priceForeignDefault;
    }

    public void setPriceForeignDefault(BigDecimal priceForeignDefault) {
        this.priceForeignDefault = priceForeignDefault;
    }
}
