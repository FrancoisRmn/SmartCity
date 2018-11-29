package com.henallux.smartcity.model;

public class Market {
    private int commerceId;
    private String nomCommerce;
    private Address address;

    @Override
    public String toString() {
        return "Market{" +
                "commerceId=" + commerceId +
                ", nomCommerce='" + nomCommerce + '\'' +
                ", address=" + address +
                '}';
    }

    public Market(int commerceId, String nomCommerce, Address address)
    {
        this.commerceId = commerceId;
        this.nomCommerce = nomCommerce;
        this.address = address;


    }
}
