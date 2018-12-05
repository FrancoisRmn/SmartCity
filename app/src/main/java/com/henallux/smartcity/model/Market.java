package com.henallux.smartcity.model;

public class Market {
    private int commerceId;
    private String nomCommerce;
    private String rue;
    private int numero;
    private String description;

    public Market(int commerceId, String nomCommerce)
    {
        this.commerceId = commerceId;
        this.nomCommerce = nomCommerce;
    }
    @Override
    public String toString() {
        return  nomCommerce ;
    }
    public String getNomCommerce()
    {
        return this.nomCommerce;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
