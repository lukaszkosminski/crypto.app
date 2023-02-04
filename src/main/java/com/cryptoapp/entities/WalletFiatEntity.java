package com.cryptoapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WalletFiatEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Integer usd;
    private Integer pln;
    private Integer euro;
    private Integer gpb;

    public Integer getUsd() {
        return usd;
    }

    public void setUsd(Integer usd) {
        this.usd = usd;
    }

    public Integer getPln() {
        return pln;
    }

    public void setPln(Integer pln) {
        this.pln = pln;
    }

    public Integer getEuro() {
        return euro;
    }

    public void setEuro(Integer euro) {
        this.euro = euro;
    }

    public Integer getGpb() {
        return gpb;
    }

    public void setGpb(Integer gpb) {
        this.gpb = gpb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
