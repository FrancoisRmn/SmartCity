package com.henallux.smartcity.model;

public class Market {
    private int commerceId;
    private String nomCommerce;
    private Address address;

    public Market(int commerceId, String nomCommerce, Address address)
    {
        this.commerceId = commerceId;
        this.nomCommerce = nomCommerce;
        this.address = address;
    }
}
