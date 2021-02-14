

package com.myretail.model;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Price {

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Price(){}
    public Price(double value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    @NotNull
    @JsonProperty
    private double value;

    @NotNull
    @JsonProperty
    private String currencyCode;


}
