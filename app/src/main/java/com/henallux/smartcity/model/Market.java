package com.henallux.smartcity.model;

public class Market {
    private int commerceId;
    private String nomCommerce;

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

    private String rue;
    private int numero;

    @Override
    public String toString() {
        return  nomCommerce ;
    }

    public Market(int commerceId, String nomCommerce)
    {
        this.commerceId = commerceId;
        this.nomCommerce = nomCommerce;
    }

    public String getNomCommerce()
    {
        return this.nomCommerce;
    }
}
