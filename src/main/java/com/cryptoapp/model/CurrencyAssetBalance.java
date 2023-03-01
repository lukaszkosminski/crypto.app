package com.cryptoapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CurrencyAssetBalance {

//    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
//    private BigDecimal currencyBalance;
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    private Map<String, BigDecimal> currencyBalance;

    public CurrencyAssetBalance() {
        currencyBalance = new HashMap<>();
    }

    public Map<String, BigDecimal> getBalances() {
        return currencyBalance;
    }

    public void setBalances(Map<String, BigDecimal> balances) {
        this.currencyBalance = balances;
    }

}
